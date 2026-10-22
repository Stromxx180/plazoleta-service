package com.example.plazoleta_service.infrastructure.adapters.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestauranteRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^(?!^[0-9]+$).*", message = "El nombre no puede ser solo números")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El NIT debe ser numérico")
    private String nit;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Formato de teléfono inválido (máx 13 caracteres)")
    private String telefono;

    @NotBlank(message = "La URL del logo es obligatoria")
    private String urlLogo;

    @NotNull(message = "El ID del propietario es obligatorio")
    private Long idPropietario;
}