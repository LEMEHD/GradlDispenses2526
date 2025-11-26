package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "external_course", indexes = @Index(columnList = "etablissement,code"))
public class ExternalCourse extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ExemptionRequest request;

    @NotBlank @Column(nullable = false)
    private String etablissement; // ULB, HE2B, ...

    @NotBlank @Column(nullable = false, length = 32)
    private String code;          // code du cours externe (clé de correspondance)

    @NotBlank @Column(nullable = false)
    private String libelle;

    @Min(1) @Column(nullable = false)
    private int ects;

    private String urlProgramme;  // lien/chemin vers la fiche
    
    // Règles pour Set dans ExemptionRequest
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                  // même instance
        if (o == null || getClass() != o.getClass()) return false;
        ExternalCourse other = (ExternalCourse) o;
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