package org.isfce.pid.GradleDispenses2526.dto;

import jakarta.validation.constraints.*;

public record AddDocumentDTO(
        @NotBlank String type, // BULLETIN/PROGRAMME/MOTIVATION/AUTRE
        @NotBlank String url
) { }