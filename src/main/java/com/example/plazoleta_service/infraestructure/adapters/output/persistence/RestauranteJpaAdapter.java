package com.example.plazoleta_service.infrastructure.output.persistence;

import com.example.plazoleta_service.application.ports.output.RestauranteRepositoryPort;
import com.example.plazoleta_service.domain.models.Restaurante;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteJpaAdapter implements RestauranteRepositoryPort {

    private final SpringDataRestauranteRepository repository;

    public RestauranteJpaAdapter(SpringDataRestauranteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurante guardar(Restaurante r) {
        RestauranteEntity entity = new RestauranteEntity(
                r.getId(), r.getNombre(), r.getNit(), r.getDireccion(),
                r.getTelefono(), r.getUrlLogo(), r.getIdPropietario()
        );
        RestauranteEntity saved = repository.save(entity);
        r.setId(saved.getId());
        return r;
    }

    @Override
    public boolean existePorNit(String nit) {
        return repository.existsByNit(nit);
    }

    @Override
    public Optional<Restaurante> obtenerPorId(Long id) {
        return repository.findById(id).map(e -> new Restaurante(
                e.getId(), e.getNombre(), e.getNit(), e.getDireccion(),
                e.getTelefono(), e.getUrlLogo(), e.getIdPropietario()
        ));
    }

    @Override
    public List<Restaurante> listarPaginadosPorNombreAsc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nombre"));
        return repository.findAll(pageable)
                .stream()
                .map(e -> new Restaurante(
                        e.getId(), e.getNombre(), e.getNit(), e.getDireccion(),
                        e.getTelefono(), e.getUrlLogo(), e.getIdPropietario()
                ))
                .toList();
    }
}