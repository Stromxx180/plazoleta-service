package com.example.plazoleta_service.application.services;

import com.example.plazoleta_service.application.ports.input.RestauranteUseCase;
import com.example.plazoleta_service.application.ports.output.RestauranteRepositoryPort;
import com.example.plazoleta_service.domain.exceptions.DomainException;
import com.example.plazoleta_service.domain.models.Restaurante;

import java.util.List;

public class RestauranteService implements RestauranteUseCase {

    private static final String REGEX_NUMERICO = "^\\d+$";
    private static final String REGEX_TELEFONO = "^\\+?\\d{1,13}$";
    private static final String REGEX_SOLO_NUMEROS = "^\\d+$";

    private final RestauranteRepositoryPort restauranteRepositoryPort;

    public RestauranteService(RestauranteRepositoryPort restauranteRepositoryPort) {
        this.restauranteRepositoryPort = restauranteRepositoryPort;
    }

    @Override
    public void crearRestaurante(Restaurante r) {
        if (r.getNombre() == null || r.getNombre().isBlank() || r.getNombre().trim().matches(REGEX_SOLO_NUMEROS)) {
            throw new DomainException("El nombre del restaurante no puede contener solo números.");
        }
        if (r.getNit() == null || !r.getNit().matches(REGEX_NUMERICO)) {
            throw new DomainException("El NIT debe ser únicamente numérico.");
        }
        if (r.getTelefono() == null || r.getTelefono().length() > 13 || !r.getTelefono().matches(REGEX_TELEFONO)) {
            throw new DomainException("El teléfono debe tener máximo 13 caracteres y solo números.");
        }
        if (r.getIdPropietario() == null) {
            throw new DomainException("El ID del propietario es obligatorio.");
        }
        if (restauranteRepositoryPort.existePorNit(r.getNit())) {
            throw new DomainException("Ya existe un restaurante registrado con ese NIT.");
        }

        restauranteRepositoryPort.guardar(r);
    }

    @Override
    public List<Restaurante> listarRestaurantes(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new DomainException("El número de página debe ser >= 0 y el tamaño > 0.");
        }
        return restauranteRepositoryPort.listarPaginadosPorNombreAsc(page, size);
    }
}