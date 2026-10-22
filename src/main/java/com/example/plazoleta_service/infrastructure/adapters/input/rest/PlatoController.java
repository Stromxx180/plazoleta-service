package com.example.plazoleta_service.infrastructure.adapters.input.rest;

import com.example.plazoleta_service.application.ports.input.PlatoUseCase;
import com.example.plazoleta_service.domain.models.Plato;
import com.example.plazoleta_service.infrastructure.adapters.input.rest.dto.PlatoEstadoDto;
import com.example.plazoleta_service.infrastructure.adapters.input.rest.dto.PlatoRequestDto;
import com.example.plazoleta_service.infrastructure.adapters.input.rest.dto.PlatoUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/platos")
public class PlatoController {

    private final PlatoUseCase platoUseCase;

    public PlatoController(PlatoUseCase platoUseCase) {
        this.platoUseCase = platoUseCase;
    }

    private Long obtenerIdPropietarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() instanceof Number idUsuario) {
            return idUsuario.longValue();
        }
        return 1L;
    }

    @Operation(summary = "HU-03: Crear plato")
    @PostMapping
    public ResponseEntity<Void> crearPlato(@Valid @RequestBody PlatoRequestDto dto) {
        Plato p = new Plato(null, dto.getNombre(), dto.getPrecio(), dto.getDescripcion(),
                dto.getUrlImagen(), dto.getCategoria(), true, dto.getIdRestaurante());
        platoUseCase.crearPlato(p, obtenerIdPropietarioActual());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "HU-04: Modificar plato")
    @PutMapping("/{id}")
    public ResponseEntity<Void> modificarPlato(@PathVariable Long id, @RequestBody PlatoUpdateDto dto) {
        platoUseCase.modificarPlato(id, dto.getPrecio(), dto.getDescripcion(), obtenerIdPropietarioActual());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "HU-07: Habilitar/Deshabilitar plato")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestBody PlatoEstadoDto dto) {
        platoUseCase.cambiarEstadoPlato(id, dto.getActivo(), obtenerIdPropietarioActual());
        return ResponseEntity.ok().build();
    }
}