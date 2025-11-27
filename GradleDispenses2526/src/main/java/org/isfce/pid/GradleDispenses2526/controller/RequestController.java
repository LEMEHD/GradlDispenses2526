package org.isfce.pid.GradleDispenses2526.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.isfce.pid.GradleDispenses2526.dto.*;
import org.isfce.pid.GradleDispenses2526.model.ExemptionRequest;
import org.isfce.pid.GradleDispenses2526.model.ExternalCourse;
import org.isfce.pid.GradleDispenses2526.model.SupportingDocument;
import org.isfce.pid.GradleDispenses2526.model.TypeDocument;
import org.isfce.pid.GradleDispenses2526.model.UE;
import org.isfce.pid.GradleDispenses2526.service.ExemptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * API Étudiant : Créer, compléter, soumettre et consulter les demandes de dispense.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestController {

    private final ExemptionService svc;

    // ——— CRÉATION D'UNE DEMANDE (Brouillon) ———
    @PostMapping("/requests")
    public ResponseEntity<ExemptionRequestDTO> create(
            @Valid @RequestBody CreateRequestDTO dto,
            @Parameter(hidden = true) Authentication auth // <--- On cache pour Swagger
    ) {
        // Authentication vient de Spring Security (on le configurera à l'étape suivante)
        String email = (auth != null) ? auth.getName() : "etudiant@isfce.be"; // Fallback temporaire pour tests sans sécu
        
        ExemptionRequest created = svc.createDraft(email, dto.section());
        return ResponseEntity
                .created(URI.create("/api/requests/" + created.getId()))
                .body(ExemptionRequestDTO.of(created));
    }
    
    // ——— AJOUT D'UN COURS EXTERNE ———
    @PostMapping("/requests/{id}/courses")
    public ExternalCourseDTO addCourse(
            @PathVariable UUID id,
            @Valid @RequestBody AddExternalCourseDTO dto
    ) {
        ExternalCourse c = svc.addExternalCourse(
            id, 
            dto.etablissement(), 
            dto.code(), 
            dto.libelle(), 
            dto.ects(), 
            dto.urlProgramme()
        );
        return ExternalCourseDTO.of(c);
    }
    
    // ——— AJOUT D'UN DOCUMENT ———
    @PostMapping("/requests/{id}/documents")
    public SupportingDocumentDTO addDoc(
            @PathVariable UUID id,
            @Valid @RequestBody AddDocumentDTO dto
    ) {
    	TypeDocument type = TypeDocument.valueOf(dto.type().toUpperCase());
        SupportingDocument doc = svc.addDocument(id, type, dto.url());
        return SupportingDocumentDTO.of(doc);
    }

    // ——— SOUMISSION (Avec calcul automatique des correspondances) ———
    @PostMapping("/requests/{id}/submit")
    public ExemptionRequestDTO submit(@PathVariable UUID id) {
    	return svc.submit(id);
    }

	// ——— LISTER MES DEMANDES ———
    @GetMapping("/requests/mine")
    public List<ExemptionRequestDTO> mine(
    		@Parameter(hidden = true) Authentication auth
    		) {
        String email = (auth != null) ? auth.getName() : "etudiant@isfce.be";
        return svc.myRequests(email);
    }
    
    // ——— CONSULTER UNE DEMANDE PRÉCISE ———
    @GetMapping("/requests/{id}")
    public ExemptionRequestDTO one(@PathVariable UUID id) {
    	ExemptionRequest r = svc.get(id);
        return ExemptionRequestDTO.of(r);
    }

    // ——— CATALOGUE DES UE ISFCE ———
    @GetMapping("/ue")
    public List<UE> allUE() {
        return svc.listUE();
    }

    // ——— DÉTAIL D'UNE UE (Nouveau !) ———
    @GetMapping("/ue/{code}")
    public UE oneUE(@PathVariable String code) {
        return svc.getUE(code);
    }
}