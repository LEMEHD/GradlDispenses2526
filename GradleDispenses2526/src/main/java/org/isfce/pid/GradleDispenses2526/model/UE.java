package org.isfce.pid.GradleDispenses2526.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "acquis")
@Entity(name = "TUE")
@Getter
public class UE {
	@Id
	@Column(length = 20)
	private String code;// IPAP
	@Column(unique = true, nullable = false, length = 20)
	private String ref;// 7521 05 U32 D3
	@Column(nullable = false, length = 50)
	private String nom;// PRINCIPES ALGORITHMIQUES ET PROGRAMMATION
	@Column(nullable = false)
	@Min(value = 1, message = "{err.cours.nbPeriodes}")
	private int nbPeriodes;// 120
	@Column(nullable = false)
	@Min(value = 1, message = "{err.cours.nbECTS}")
	private int ects;// 8
	@Lob
	private String prgm;// Bloc de texte
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FKUE")
	private List<Acquis> acquis;
}
