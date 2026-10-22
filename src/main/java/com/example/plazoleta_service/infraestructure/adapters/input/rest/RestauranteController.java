package com.example.plazoleta_service.infrastructure.adapters.input.rest;

import com.example.plazoleta_service.application.ports.input.RestauranteUseCase;
import com.example.plazoleta_service.domain.models.Restaurante;
import com.example.plazoleta_service.infrastructure.adapters.input.rest.dto.RestauranteRequestDto;
import com.example.plazoleta_service.infrastructure.adapters.input.rest.dto.RestauranteResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final RestauranteUseCase restauranteUseCase;

    public RestauranteController(RestauranteUseCase restauranteUseCase) {
        this.restauranteUseCase = restauranteUseCase;
    }

    @Operation(summary = "HU-02: Crear restaurante (Solo Admin)")
    @PostMapping
    public ResponseEntity<Void> crearRestaurante(@Valid @RequestBody RestauranteRequestDto dto) {
        Restaurante r = new Restaurante(null, dto.getNombre(), dto.getNit(), dto.getDireccion(),
                dto.getTelefono(), dto.getUrlLogo(), dto.getIdPropietario());
        restauranteUseCase.crearRestaurante(r);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "HU-09: Listar restaurantes paginados y ordenados alfabéticamente")
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDto>> listarRestaurantes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RestauranteResponseDto> list = restauranteUseCase.listarRestaurantes(page, size)
                .stream()
                .map(r -> new RestauranteResponseDto(r.getNombre(), r.getUrlLogo()))
                .toList();
        return ResponseEntity.ok(list);
    }
}