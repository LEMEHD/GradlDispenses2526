package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true) // Important pour BaseEntity
@Entity
@Table(name = "TACQUIS")
public class Acquis extends BaseEntity {
	
    // L'ID est hérité de BaseEntity (UUID)

    @NotBlank(message = "{err.acquis.desc.blank}")
    @Column(length = 500, nullable = false)
    private String description; // Ex: "Maîtriser les boucles"
	
    @Min(value = 1, message = "{err.acquis.pct.min}")
    @Max(value = 100, message = "{err.acquis.pct.max}")
    @Column(nullable = false)
    private Integer pourcentage;
	
    // Constructeur pratique pour tes tests/SeedData
    public Acquis(String description, int pourcentage) {
        this.description = description;
        this.pourcentage = pourcentage;
    }
}