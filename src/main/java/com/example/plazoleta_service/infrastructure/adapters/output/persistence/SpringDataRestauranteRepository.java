package com.example.plazoleta_service.infrastructure.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
    boolean existsByNit(String nit);
}