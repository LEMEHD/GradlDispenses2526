package org.isfce.pid.GradleDispenses2526.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Génère Getter, Setter, etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
// CORRECTION LOMBOK : On inclut le parent (l'ID) dans l'égalité, mais on exclut toujours la liste (pour éviter les boucles infines)
@EqualsAndHashCode(callSuper = true, exclude = "acquis")
@ToString(exclude = "acquis") // Bonne pratique : on exclut aussi les listes du toString
@Entity(name = "TUE")
public class UE extends BaseEntity { // <--- On étend BaseEntity

    @NotBlank(message = "{err.ue.code.vide}")
    // CORRECTION JPA : Ce n'est PLUS un @Id, mais une colonne unique obligatoire
    @Column(length = 20, unique = true, nullable = false)
    private String code; // IPAP

    @Column(unique = true, nullable = false, length = 20)
    private String ref; // 7521 05 U32 D3

    @NotBlank(message = "{err.ue.nom.vide}")
    @Column(nullable = false, length = 50)
    private String nom; // PRINCIPES ALGORITHMIQUES...

    @Min(value = 1, message = "{err.cours.nbPeriodes}")
    @Column(nullable = false)
    private int nbPeriodes; // 120

    @Min(value = 1, message = "{err.ue.ects.min}")
    @Column(nullable = false)
    private int ects; // 8

    @Lob
    private String prgm; // Bloc de texte

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FKUE")
    private List<Acquis> acquis;
    
}