package com.example.plazoleta_service.application.ports.output;

import com.example.plazoleta_service.domain.models.Restaurante;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepositoryPort {
    Restaurante guardar(Restaurante restaurante);
    boolean existePorNit(String nit);
    Optional<Restaurante> obtenerPorId(Long id);
    List<Restaurante> listarPaginadosPorNombreAsc(int page, int size);
}