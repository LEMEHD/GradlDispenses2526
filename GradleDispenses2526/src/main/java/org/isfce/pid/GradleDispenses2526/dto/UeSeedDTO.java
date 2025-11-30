package org.isfce.pid.GradleDispenses2526.dto;

import java.util.List;

public record UeSeedDTO(
	    String code, 
	    String ref, 
	    String nom, 
	    int ects, 
	    int nbPeriodes, 
	    int niveau,
	    String prgm, 
	    List<AcquisSeedDTO> acquis
	) {}
