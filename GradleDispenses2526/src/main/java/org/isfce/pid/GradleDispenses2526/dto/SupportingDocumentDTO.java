package org.isfce.pid.GradleDispenses2526.dto;

import org.isfce.pid.GradleDispenses2526.model.SupportingDocument;

import java.util.UUID;

/** DTO renvoyé au client après ajout d'un document. */
public record SupportingDocumentDTO(
        UUID id,
        String type,          // BULLETIN / PROGRAMME / MOTIVATION / AUTRE
        String urlStockage
) {
    public static SupportingDocumentDTO of(SupportingDocument d) {
        return new SupportingDocumentDTO(
                d.getId(),
                d.getType().name(),
                d.getUrlStockage()
        );
    }
}