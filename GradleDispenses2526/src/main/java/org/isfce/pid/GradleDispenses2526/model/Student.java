package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "STUDENT", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Student extends BaseEntity {

    @NotBlank @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String prenom;
    private String nom;
}
