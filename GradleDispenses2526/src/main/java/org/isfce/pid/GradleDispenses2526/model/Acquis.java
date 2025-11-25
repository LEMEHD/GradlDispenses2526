package org.isfce.pid.GradleDispenses2526.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TACQUIS")
public class Acquis {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 500)
	private String acquis;
	
	@Min(value = 1, message = "{err.aquis.pourcentage.min}")
	@Max(value = 100, message = "{err.aquis.pourcentage.max}")
	private Integer pourcentage;
	
	
	public Acquis(String acquis, int pourcentage) {
		this.acquis = acquis;
		this.pourcentage = pourcentage;
	}
}
