package org.isfce.pid.GradleDispenses2526.seed;

import org.isfce.pid.GradleDispenses2526.dao.*;
import org.isfce.pid.GradleDispenses2526.model.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
public class SeedData {

    // On injecte tout via le constructeur (plus propre)
    private final IUeDao ueDao;
    private final IKbSchoolDao schoolDao;
    private final IKbCourseDao courseDao;
    private final IKbCorrespondenceRuleDao ruleDao;

    public SeedData(IUeDao ueDao, IKbSchoolDao schoolDao, IKbCourseDao courseDao, IKbCorrespondenceRuleDao ruleDao) {
        this.ueDao = ueDao;
        this.schoolDao = schoolDao;
        this.courseDao = courseDao;
        this.ruleDao = ruleDao;
    }

    @Bean
    ApplicationRunner runner() {
        return args -> {
            System.out.println("🌱 SEEDING : Démarrage de l'initialisation...");
            
            // 1. Initialiser les UE ISFCE (Le catalogue interne)
            seedUEs();

            // 2. Initialiser la Base de Connaissances (Écoles et Règles)
            if (schoolDao.count() == 0) {
                seedKnowledgeBase();
            }

            System.out.println("✅ SEEDING TERMINÉ : Base de données prête !");
        };
    }

    @Transactional // Important pour gérer les relations proprement
    public void seedUEs() {
        // UE Complexe (IPAP)
        if (!ueDao.existsByCode("IPAP")) {
            createIPAP();
        }
        // UEs Administratives (Liste du PDF)
        createUeIfMissing("IPID", "Projet d'intégration de développement", 8, 120);
        createUeIfMissing("POO",  "Programmation Orientée Objet", 6, 80);
        createUeIfMissing("IIBD", "Initiation aux Bases de Données", 5, 60);
        createUeIfMissing("ISE2", "Systèmes d'exploitation", 5, 60);
        createUeIfMissing("IPAI", "Analyse Informatique", 5, 60);
        createUeIfMissing("XORG", "Organisation des entreprises", 5, 60);
        createUeIfMissing("ISO2", "Structure des ordinateurs", 3, 40);
        createUeIfMissing("IMA2", "Mathématiques appliquées", 4, 40);
        createUeIfMissing("IWPB", "Web : Principes de base", 6, 80);
        createUeIfMissing("IPO2", "Projet orienté objet", 3, 40); 
        createUeIfMissing("IBR2", "Bases des réseaux", 5, 60);
        createUeIfMissing("MATH", "Mathématiques générales", 4, 40); // Cible pour l'exemple complexe
        createUeIfMissing("RESO", "Réseaux et communication", 4, 40); // Cible pour l'exemple complexe
    }

    @Transactional
    public void seedKnowledgeBase() {
        // 1. Création des Écoles (On garde les objets en mémoire pour les réutiliser)
        KbSchool vinci = createSchool("VINCI", "École Léonard de Vinci", "https://www.vinci.be/en");
        KbSchool ulb   = createSchool("ULB", "Université Libre de Bruxelles", "https://www.ulb.be");
        KbSchool he2b  = createSchool("HE2B", "HE2B ESI", "https://www.he2b.be");
        KbSchool ephec = createSchool("EPHEC", "EPHEC", "https://www.ephec.be");
        KbSchool helb  = createSchool("HELB", "HELB Prigogine", "https://helb.be");

        // 2. Règles Simples (1 pour 1) - Tableaux du PDF
        // VINCI
        createSimpleRule(vinci, "BINV2090-2", "Projet d'intégration", 8, "IPID");
        createSimpleRule(vinci, "BINV1020-1", "POO", 6, "POO");
        createSimpleRule(vinci, "BINV1010-1", "Algo et prog", 6, "IPAP");
        createSimpleRule(vinci, "BINV1030-1", "Bases de données", 5, "IIBD");
        createSimpleRule(vinci, "BINV1060-1", "Systèmes d'OS", 5, "ISE2");
        createSimpleRule(vinci, "BINV2040-1", "Analyse", 5, "IPAI");
        createSimpleRule(vinci, "BINV1080-2", "Organisation", 5, "XORG");
        createSimpleRule(vinci, "BINV1073-1", "Structure ordis", 3, "ISO2");
        createSimpleRule(vinci, "BINV-1090-1", "Maths", 4, "IMA2");
        createSimpleRule(vinci, "BINV1052-1", "Web", 1, "IWPB");

        // ULB
        createSimpleRule(ulb, "INFO-F101", "Programmation", 10, "IPAP");
        createSimpleRule(ulb, "INFO-F102", "Fonct. des ordinateurs", 5, "ISO2");
        createSimpleRule(ulb, "INFO-H303", "Bases de données", 5, "IPAI");

        // HE2B
        createSimpleRule(he2b, "2DON1A", "Base de données", 4, "IIBD");
        createSimpleRule(he2b, "2DEV2A", "Dév 2", 6, "IPO2");
        createSimpleRule(he2b, "1ALG1A", "Algo 1", 4, "IPAP"); // Note: Seulement 4 ECTS -> NEEDS_REVIEW
        createSimpleRule(he2b, "2WEB1A", "Web 1", 6, "IWPB");
        createSimpleRule(he2b, "1MAT1A", "Maths", 4, "IMA2");
        createSimpleRule(he2b, "1EXP1A", "Systèmes", 5, "ISE2");

        // EPHEC
        createSimpleRule(ephec, "RESEAUX1", "Réseaux 1", 5, "IBR2");
        createSimpleRule(ephec, "SYS-EXP1", "Systèmes OS 1", 5, "ISE2");

        // HELB
        createSimpleRule(helb, "IODA0101-2", "Algo & Prog", 5, "IPAP");
        createSimpleRule(helb, "IODA0107-1", "Projet", 3, "IPO2");

        // 3. Règles Complexes (Pour tes tests de logique avancée)
        seedComplexCases(he2b, ephec);
    }

    private void seedComplexCases(KbSchool he2b, KbSchool ephec) {
        // --- Cas N vers 1 (Le Puzzle) : HE2B Algo 1 + Algo 2 -> IPAP ---
        // On réutilise le cours "1ALG1A" déjà créé plus haut, on crée juste le 2ème
        KbCourse algo1 = courseDao.findByEcoleAndCodeIgnoreCase(he2b, "1ALG1A").orElseThrow();
        KbCourse algo2 = createCourse(he2b, "2ALG2A", "Algo 2", 4);

        createComplexRule(he2b, 
            "HE2B Algo 1 + Algo 2 -> IPAP", 
            8, // ECTS Requis
            List.of(algo1, algo2), // Sources
            List.of("IPAP")        // Cibles
        );

        // --- Cas 1 vers N (Le Couteau Suisse) : EPHEC Infra -> ISE2 + IBR2 ---
        KbCourse infra = createCourse(ephec, "INFRA-GLOBAL", "Infrastructure IT Globale", 10);

        createComplexRule(ephec,
            "EPHEC Infra -> Systèmes + Réseaux",
            10, // ECTS Requis
            List.of(infra),         // Sources
            List.of("ISE2", "IBR2") // Cibles
        );
    }


    // ===================================================================================
    //                              HELPER METHODS (LA BOÎTE A OUTILS)
    // ===================================================================================

    private void createUeIfMissing(String code, String nom, int ects, int periodes) {
        if (!ueDao.existsByCode(code)) {
            ueDao.save(UE.builder()
                    .code(code).ref("REF-" + code).nom(nom).ects(ects).nbPeriodes(periodes)
                    .build());
        }
    }

    private KbSchool createSchool(String code, String nom, String url) {
        return schoolDao.save(KbSchool.builder().code(code).etablissement(nom).urlProgramme(url).build());
    }
    
    private KbCourse createCourse(KbSchool school, String code, String libelle, int ects) {
        // Vérifie si existe déjà pour éviter doublons si appel multiple
        return courseDao.findByEcoleAndCodeIgnoreCase(school, code)
                .orElseGet(() -> courseDao.save(KbCourse.builder()
                        .ecole(school).code(code).libelle(libelle).ects(ects)
                        .urlProgramme("https://prog." + school.getCode() + ".be/" + code)
                        .build()));
    }

    // Helper pour le cas standard 1-1
    private void createSimpleRule(KbSchool school, String extCode, String extLibelle, int extEcts, String targetUeCode) {
        KbCourse course = createCourse(school, extCode, extLibelle, extEcts);
        createComplexRule(school, school.getCode() + " " + extCode + " -> " + targetUeCode, extEcts, List.of(course), List.of(targetUeCode));
    }

    // Helper générique pour TOUS les cas (1-1, N-1, 1-N)
    private void createComplexRule(KbSchool school, String description, int minEcts, List<KbCourse> sources, List<String> targetUeCodes) {
        KbCorrespondenceRule rule = KbCorrespondenceRule.builder()
                .ecole(school)
                .description(description)
                .minTotalEcts(minEcts)
                .build();

        // Ajout des sources
        sources.forEach(src -> rule.addSource(KbCorrespondenceRuleSource.builder().rule(rule).cours(src).build()));
        
        // Ajout des cibles
        targetUeCodes.forEach(ueCode -> {
            ueDao.findByCode(ueCode).ifPresent(ue -> 
                rule.addTarget(KbCorrespondenceRuleTarget.builder().rule(rule).ue(ue).build())
            );
        });

        ruleDao.save(rule);
    }

    private void createIPAP() {
        String prgm = "* d'identifier différents langages de programmation existants ..."; // Abrégé
        UE pap = UE.builder()
                .code("IPAP").ref("7521 05 U32 D3").nom("PRINCIPES ALGORITHMIQUES ET PROGRAMMATION")
                .ects(8).nbPeriodes(120).prgm(prgm).build();

        pap.setAcquis(List.of(
            new Acquis("mettre en oeuvre une représentation algorithmique", 30),
            new Acquis("développer au moins un programme", 30),
            new Acquis("procédures de test", 20),
            new Acquis("justifier la démarche", 20)
        ));
        ueDao.save(pap);
    }
}