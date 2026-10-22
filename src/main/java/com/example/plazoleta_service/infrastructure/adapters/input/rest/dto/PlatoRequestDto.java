package com.example.plazoleta_service.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlatoRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String urlImagen;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El ID del restaurante es obligatorio")
    private Long idRestaurante;
}