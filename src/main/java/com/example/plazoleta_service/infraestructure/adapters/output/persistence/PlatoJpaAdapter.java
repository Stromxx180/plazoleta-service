package com.example.plazoleta_service.infrastructure.output.persistence;

import com.example.plazoleta_service.application.ports.output.PlatoRepositoryPort;
import com.example.plazoleta_service.domain.models.Plato;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlatoJpaAdapter implements PlatoRepositoryPort {

    private final SpringDataPlatoRepository repository;

    public PlatoJpaAdapter(SpringDataPlatoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Plato guardar(Plato p) {
        PlatoEntity entity = new PlatoEntity(
                p.getId(), p.getNombre(), p.getPrecio(), p.getDescripcion(),
                p.getUrlImagen(), p.getCategoria(), p.getActivo(), p.getIdRestaurante()
        );
        PlatoEntity saved = repository.save(entity);
        p.setId(saved.getId());
        return p;
    }

    @Override
    public Optional<Plato> obtenerPorId(Long id) {
        return repository.findById(id).map(e -> new Plato(
                e.getId(), e.getNombre(), e.getPrecio(), e.getDescripcion(),
                e.getUrlImagen(), e.getCategoria(), e.getActivo(), e.getIdRestaurante()
        ));
    }
}