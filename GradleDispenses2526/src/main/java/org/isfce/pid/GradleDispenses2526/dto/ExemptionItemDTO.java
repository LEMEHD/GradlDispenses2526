package org.isfce.pid.GradleDispenses2526.dto;

import org.isfce.pid.GradleDispenses2526.model.ExemptionItem;

import java.util.UUID;

public record ExemptionItemDTO(
        UUID id,
        String ueCode,
        String ueLibelle,
        int ueEcts,
        boolean totalEctsMatches,
        String decision,      // PENDING / AUTO_ACCEPTED / ...
        Integer noteSur20
) {
    public static ExemptionItemDTO of(ExemptionItem i) {
        return new ExemptionItemDTO(
                i.getId(),
                i.getUe().getCode(),
                i.getUe().getNom(),
                i.getUe().getEcts(),
                i.isTotalEctsMatches(),
                i.getDecision().name(),
                i.getNoteSur20()
        );
    }
}