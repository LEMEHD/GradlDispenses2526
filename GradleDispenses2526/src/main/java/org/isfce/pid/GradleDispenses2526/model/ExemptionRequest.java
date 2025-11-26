package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "exemption_request")
public class ExemptionRequest extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Student etudiant;

    @NotBlank @Column(nullable = false)
    private String section; // ex: Informatique

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    @Builder.Default
    private StatutDemande statut = StatutDemande.DRAFT;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default @ToString.Exclude
    private Set<ExternalCourse> externalCourses = new HashSet<>();

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default @ToString.Exclude
    private Set<SupportingDocument> documents = new HashSet<>();

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default @ToString.Exclude
    private Set<ExemptionItem> items = new HashSet<>();

    // Helpers pratiques
    public void addExternalCourse(ExternalCourse c){ c.setRequest(this); externalCourses.add(c); }
    public void addDocument(SupportingDocument d){ d.setRequest(this); documents.add(d); }
    public void addItem(ExemptionItem i){ i.setRequest(this); items.add(i); }
}