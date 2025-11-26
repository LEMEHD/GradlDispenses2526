package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "supporting_document")
public class SupportingDocument extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ExemptionRequest request;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TypeDocument type;

    @NotBlank @Column(nullable = false)
    private String urlStockage;
    
 // Règles pour Set dans ExemptionRequest
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                  // même instance
        if (o == null || getClass() != o.getClass()) return false;
        SupportingDocument other = (SupportingDocument) o;
        if (this.getId() == null || other.getId() == null) {
            // Avant persist: id null → NE PAS les considérer égaux
            return false;
        }
        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        // Avant persist (id null) : renvoyer un hash stable lié à la classe
        // Après persist : hash = id.hashCode()
        return (getId() == null) ? System.identityHashCode(this) : getId().hashCode();
    }
}