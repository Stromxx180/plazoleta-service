package com.example.plazoleta_service.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlatoUpdateDto {
    @NotNull @Positive
    private Integer precio;

    @NotBlank
    private String descripcion;
}