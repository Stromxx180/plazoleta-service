package com.example.plazoleta_service.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlatoUpdateDto {
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
}