package com.example.plazoleta_service.infrastructure.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPlatoRepository extends JpaRepository<PlatoEntity, Long> {
}