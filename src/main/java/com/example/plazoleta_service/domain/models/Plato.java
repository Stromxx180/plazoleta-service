package com.example.plazoleta_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    private Long id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private String categoria;
    private Boolean activo;
    private Long idRestaurante;
}