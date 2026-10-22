package com.example.plazoleta_service.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatoEstadoDto {
    @NotNull
    private Boolean activo;
}