package com.example.plazoleta_service.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestauranteResponseDto {
    private String nombre;
    private String urlLogo;
}