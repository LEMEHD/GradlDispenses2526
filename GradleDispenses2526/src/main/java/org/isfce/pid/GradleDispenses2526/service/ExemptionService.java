package org.isfce.pid.GradleDispenses2526.service;

import lombok.RequiredArgsConstructor;
import org.isfce.pid.GradleDispenses2526.dao.*;
import org.isfce.pid.GradleDispenses2526.model.*;
import org.isfce.pid.GradleDispenses2526.dto.ExemptionRequestDTO; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExemptionService {

    // On injecte tous les DAOs nécessaires
    private final IEtudiantDao etuDao;
    private final IUeDao ueDao;
    private final IExemptionRequestDao reqDao;
    private final IExternalCourseDao extCourseDao;
    private final ISupportingDocumentDao docDao;
    private final IExemptionItemDao itemDao;
    
    // Les DAOs pour la "Base de Connaissances" (Knowledge Base)
    private final IKbSchoolDao kbSchoolDao;
    private final IKbCourseDao kbCourseDao;
    private final IKbCorrespondenceRuleDao kbRuleDao;

    // ————— Flux Étudiant —————

    public Student getOrCreateByEmail(String email) {
        return etuDao.findByEmail(email)
                .orElseGet(() -> etuDao.save(Student.builder().email(email).build()));
    }

    public ExemptionRequest createDraft(String email, String section) {
        Student e = getOrCreateByEmail(email);
        ExemptionRequest req = ExemptionRequest.builder().etudiant(e).section(section).build();
        return reqDao.save(req);
    }

    public ExternalCourse addExternalCourse(UUID requestId, String etab, String code, String libelle, int ects, String url) {
        ExemptionRequest req = reqDao.findById(requestId).orElseThrow();
        
        if (req.getStatut() != StatutDemande.DRAFT) {
            throw new IllegalStateException("Cette demande n'est plus modifiable (" + req.getStatut() + ")");
        }
        
        ExternalCourse c = ExternalCourse.builder()
                .request(req)
                .etablissement(etab)
                .code(code)
                .libelle(libelle)
                .ects(ects)
                .urlProgramme(url)
                .build();
                
        c = extCourseDao.save(c);
        req.addExternalCourse(c);
        return c;
    }

    public SupportingDocument addDocument(UUID requestId, TypeDocument type, String url) {
        ExemptionRequest req = reqDao.findById(requestId).orElseThrow();
        
        if (req.getStatut() != StatutDemande.DRAFT) {
            throw new IllegalStateException("Cette demande n'est plus modifiable (" + req.getStatut() + ")");
        }
        
        SupportingDocument d = SupportingDocument.builder()
                .request(req)
                .type(type)
                .urlStockage(url)
                .build();
                
        d = docDao.save(d);
        req.addDocument(d);
        return d;
    }

    public List<UE> listUE() { 
        return ueDao.findAll(); 
    }

    public ExemptionRequest get(UUID id) { 
        return reqDao.findById(id).orElseThrow(); 
    }

    @Transactional(readOnly = true)
    public List<ExemptionRequestDTO> myRequests(String email) {
        return reqDao.findAllByEtudiantEmail(email).stream()
                   .map(ExemptionRequestDTO::of)
                   .toList();
    }
    
    // Récupère une UE précise via son code (ex: "IPAP")
    public UE getUE(String code) {
        return ueDao.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("UE introuvable avec le code : " + code));
    }

    // ————— LE CŒUR DU SYSTÈME : SOUMISSION & MATCHING INTELLIGENT —————

    public ExemptionRequestDTO submit(UUID id) {
        ExemptionRequest req = reqDao.findById(id).orElseThrow();
        
        if (req.getStatut() != StatutDemande.DRAFT) {
            throw new IllegalStateException("Demande déjà soumise");
        }

        // 1. Validations de base
        if (req.getExternalCourses().isEmpty()) throw new IllegalStateException("Ajoute au moins un cours externe.");
        if (req.getDocuments().isEmpty()) throw new IllegalStateException("Ajoute au moins un document.");

        // 2. Reconnaissance des cours (Matching KbCourse)
        // On cherche à savoir si les cours encodés par l'étudiant existent dans notre KB
        Map<KbCourse, List<ExternalCourse>> kbToExternal = new HashMap<>();
        Set<ExternalCourse> externalCourses = req.getExternalCourses();

        for (ExternalCourse ext : externalCourses) {
            // Recherche de l'école dans la KB
            Optional<KbSchool> optSchool = kbSchoolDao.findByCodeIgnoreCase(ext.getEtablissement());
            
            if (optSchool.isPresent()) {
                // Recherche du cours dans cette école
                Optional<KbCourse> optKbCourse = kbCourseDao.findByEcoleAndCodeIgnoreCase(optSchool.get(), ext.getCode());
                
                // Si trouvé, on le stocke pour l'étape suivante
                if (optKbCourse.isPresent()) {
                    kbToExternal
                        .computeIfAbsent(optKbCourse.get(), k -> new ArrayList<>())
                        .add(ext);
                }
            }
        }

        // 3. Application des règles de correspondance
        // On regarde si une combinaison de cours reconnus déclenche une règle
        Set<KbSchool> schoolsInRequest = kbToExternal.keySet().stream()
                .map(KbCourse::getEcole)
                .collect(Collectors.toSet());

        List<KbCorrespondenceRule> applicableRules = new ArrayList<>();

        for (KbSchool school : schoolsInRequest) {
            // On charge toutes les règles connues pour cette école
            List<KbCorrespondenceRule> rules = kbRuleDao.findByEcole(school);

            for (KbCorrespondenceRule rule : rules) {
                // Vérifier si TOUS les cours nécessaires à la règle sont présents dans la demande
                boolean allSourcesPresent = rule.getSources().stream()
                        .map(KbCorrespondenceRuleSource::getCours)
                        .allMatch(kbToExternal::containsKey);

                if (!allSourcesPresent) continue;

                // Vérifier si le total des ECTS est suffisant (si la règle l'exige)
                int totalEcts = rule.getSources().stream()
                        .map(KbCorrespondenceRuleSource::getCours)
                        .mapToInt(KbCourse::getEcts)
                        .sum();

                boolean ectsOk = rule.getMinTotalEcts() == null || totalEcts >= rule.getMinTotalEcts();

                // Créer les items de dispense pour les UE cibles (Target)
                for (KbCorrespondenceRuleTarget tgt : rule.getTargets()) {
                    UE ueCible = tgt.getUe();

                    // CORRECTION : Vérifier si une dispense pour cette UE existe déjà dans la demande
                    boolean existeDeja = req.getItems().stream()
                            .anyMatch(i -> i.getUe().getCode().equals(ueCible.getCode()));

                    if (existeDeja) {
                        continue; // On passe, on a déjà traité cette UE via une autre règle
                    }

                    ExemptionItem item = ExemptionItem.builder()
                            .request(req)
                            .ue(ueCible)
                            .totalEctsMatches(ectsOk)
                            .decision(ectsOk ? DecisionItem.AUTO_ACCEPTED : DecisionItem.NEEDS_REVIEW)
                            .build();

                    itemDao.save(item);
                    req.addItem(item);
                }
                applicableRules.add(rule);
            }
        }

        // 4. Calcul du statut global
        // Si au moins un cours est reconnu et auto-accepté -> SUBMITTED (le prof verra le dossier pré-rempli)
        // Sinon -> UNDER_REVIEW (tout est à faire à la main)
        
        boolean auMoinsUnAutoAccepted = req.getItems().stream()
                .anyMatch(i -> i.getDecision() == DecisionItem.AUTO_ACCEPTED);

        if (auMoinsUnAutoAccepted) {
            req.setStatut(StatutDemande.SUBMITTED);
        } else {
            // Si aucune règle n'a marché, c'est un dossier "pur manuel"
            req.setStatut(StatutDemande.UNDER_REVIEW);
        }

        req.setUpdatedAt(Instant.now());
        reqDao.save(req);

        // Recharger pour avoir toutes les relations (via EntityGraph du DAO)
        return ExemptionRequestDTO.of(reqDao.findWithAllById(id).orElseThrow());
    }
}