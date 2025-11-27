package org.isfce.pid.GradleDispenses2526.dto;

import org.isfce.pid.GradleDispenses2526.model.ExternalCourse;
import java.util.UUID;

public record ExternalCourseDTO(
        UUID id,
        String etablissement,
        String code,
        String libelle,
        int ects,
        String urlProgramme
) {
    public static ExternalCourseDTO of(ExternalCourse c) {
        return new ExternalCourseDTO(
                c.getId(),
                c.getEtablissement(),
                c.getCode(),
                c.getLibelle(),
                c.getEcts(),
                c.getUrlProgramme()
        );
    }
}