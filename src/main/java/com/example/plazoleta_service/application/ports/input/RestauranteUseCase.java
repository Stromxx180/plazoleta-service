package com.example.plazoleta_service.application.ports.input;

import com.example.plazoleta_service.domain.models.Restaurante;
import java.util.List;

public interface RestauranteUseCase {
    void crearRestaurante(Restaurante restaurante);
    List<Restaurante> listarRestaurantes(int page, int size);
}