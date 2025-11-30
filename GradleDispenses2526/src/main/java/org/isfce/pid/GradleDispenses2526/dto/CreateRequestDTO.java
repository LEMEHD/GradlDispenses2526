package org.isfce.pid.GradleDispenses2526.dto;

import org.isfce.pid.GradleDispenses2526.model.Section;

import jakarta.validation.constraints.NotNull;

public record CreateRequestDTO(
    @NotNull(message = "La section est obligatoire") 
    Section section 
) {}