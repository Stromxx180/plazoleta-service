package com.example.plazoleta_service.infrastructure.adapters.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "platos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private String categoria;
    private Boolean activo;
    private Long idRestaurante;
}