package com.example.plazoleta_service.application.services;

import com.example.plazoleta_service.application.ports.input.PlatoUseCase;
import com.example.plazoleta_service.application.ports.output.PlatoRepositoryPort;
import com.example.plazoleta_service.application.ports.output.RestauranteRepositoryPort;
import com.example.plazoleta_service.domain.exceptions.DomainException;
import com.example.plazoleta_service.domain.models.Plato;
import com.example.plazoleta_service.domain.models.Restaurante;

public class PlatoService implements PlatoUseCase {

    private final PlatoRepositoryPort platoRepositoryPort;
    private final RestauranteRepositoryPort restauranteRepositoryPort;

    public PlatoService(PlatoRepositoryPort platoRepositoryPort,
                        RestauranteRepositoryPort restauranteRepositoryPort) {
        this.platoRepositoryPort = platoRepositoryPort;
        this.restauranteRepositoryPort = restauranteRepositoryPort;
    }

    @Override
    public void crearPlato(Plato plato, Long idPropietarioAutenticado) {
        if (plato.getPrecio() == null || plato.getPrecio() <= 0) {
            throw new DomainException("El precio del plato debe ser un número entero positivo mayor a 0.");
        }

        Restaurante restaurante = restauranteRepositoryPort.obtenerPorId(plato.getIdRestaurante())
                .orElseThrow(() -> new DomainException("El restaurante especificado no existe."));

        if (!restaurante.getIdPropietario().equals(idPropietarioAutenticado)) {
            throw new DomainException("No eres el propietario de este restaurante.");
        }

        plato.setActivo(true); // HU-03 Criterio 4: Por defecto activo en true
        platoRepositoryPort.guardar(plato);
    }

    @Override
    public void modificarPlato(Long idPlato, Integer precio, String descripcion, Long idPropietarioAutenticado) {
        if (precio == null || precio <= 0) {
            throw new DomainException("El precio debe ser un número entero positivo mayor a 0.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new DomainException("La descripción es obligatoria.");
        }

        Plato plato = obtenerYValidarPropiedad(idPlato, idPropietarioAutenticado);
        plato.setPrecio(precio);
        plato.setDescripcion(descripcion);

        platoRepositoryPort.guardar(plato);
    }

    @Override
    public void cambiarEstadoPlato(Long idPlato, Boolean activo, Long idPropietarioAutenticado) {
        if (activo == null) {
            throw new DomainException("El estado activo es obligatorio.");
        }

        Plato plato = obtenerYValidarPropiedad(idPlato, idPropietarioAutenticado);
        plato.setActivo(activo);

        platoRepositoryPort.guardar(plato);
    }

    private Plato obtenerYValidarPropiedad(Long idPlato, Long idPropietarioAutenticado) {
        Plato plato = platoRepositoryPort.obtenerPorId(idPlato)
                .orElseThrow(() -> new DomainException("El plato no existe."));

        Restaurante restaurante = restauranteRepositoryPort.obtenerPorId(plato.getIdRestaurante())
                .orElseThrow(() -> new DomainException("El restaurante asociado al plato no existe."));

        if (!restaurante.getIdPropietario().equals(idPropietarioAutenticado)) {
            throw new DomainException("No puedes modificar platos de un restaurante que no te pertenece.");
        }

        return plato;
    }
}