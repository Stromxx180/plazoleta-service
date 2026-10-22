package com.example.plazoleta_service.application.ports.input;

import com.example.plazoleta_service.domain.models.Plato;

public interface PlatoUseCase {
    void crearPlato(Plato plato, Long idPropietarioAutenticado);
    void modificarPlato(Long idPlato, Integer precio, String descripcion, Long idPropietarioAutenticado);
    void cambiarEstadoPlato(Long idPlato, Boolean activo, Long idPropietarioAutenticado);
}