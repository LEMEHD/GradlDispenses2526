package org.isfce.pid.GradleDispenses2526.dto;

import jakarta.validation.constraints.*;

public record AddExternalCourseDTO(
        @NotBlank String etablissement,
        @NotBlank String code,
        @NotBlank String libelle,
        @Min(1) int ects,
        String urlProgramme
) { }