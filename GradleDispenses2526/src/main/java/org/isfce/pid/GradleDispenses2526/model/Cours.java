package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="TCOURS")
public class Cours {
	@Id
	@Column(length = 20)
	private String code;
	@Column(nullable = false,length = 50)
	@NotBlank(message = "{err.cours.nom}")
	private String nom;
	@Column(nullable = false)
	@Min(value = 1,message = "{err.cours.nbPeriodes}")
	private short nbPeriodes;
}
