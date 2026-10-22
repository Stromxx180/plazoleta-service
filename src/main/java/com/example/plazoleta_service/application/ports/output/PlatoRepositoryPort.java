package com.example.plazoleta_service.application.ports.output;

import com.example.plazoleta_service.domain.models.Plato;
import java.util.Optional;

public interface PlatoRepositoryPort {
    Plato guardar(Plato plato);
    Optional<Plato> obtenerPorId(Long id);
}