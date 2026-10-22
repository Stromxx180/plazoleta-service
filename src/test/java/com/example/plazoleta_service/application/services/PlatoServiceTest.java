package com.example.plazoleta_service.application.services;

import com.example.plazoleta_service.application.ports.output.PlatoRepositoryPort;
import com.example.plazoleta_service.application.ports.output.RestauranteRepositoryPort;
import com.example.plazoleta_service.domain.exceptions.DomainException;
import com.example.plazoleta_service.domain.models.Plato;
import com.example.plazoleta_service.domain.models.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoServiceTest {

    @Mock
    private PlatoRepositoryPort platoRepositoryPort;

    @Mock
    private RestauranteRepositoryPort restauranteRepositoryPort;

    @InjectMocks
    private PlatoService platoService;

    private Restaurante restauranteMock;
    private Plato platoMock;

    @BeforeEach
    void setUp() {
        restauranteMock = new Restaurante(
                1L, "Sabor Caribeño", "900123456", "Calle 72", "+573001112233", "logo.png", 2L
        );

        platoMock = new Plato(
                10L, "Bandeja Paisa", 25000, "Plato típico", "img.png", "Fuertes", false, 1L
        );
    }

    // --- TESTS HU-03: CREAR PLATO ---

    @Test
    void crearPlato_Exitoso_ActivaPlatoPorDefectoYGuarda() {
        when(restauranteRepositoryPort.obtenerPorId(1L)).thenReturn(Optional.of(restauranteMock));

        platoService.crearPlato(platoMock, 2L);

        assertTrue(platoMock.getActivo());
        verify(platoRepositoryPort, times(1)).guardar(platoMock);
    }

    @Test
    void crearPlato_LanzaExcepcion_CuandoPropietarioNoEsElDueno() {
        when(restauranteRepositoryPort.obtenerPorId(1L)).thenReturn(Optional.of(restauranteMock));

        DomainException ex = assertThrows(DomainException.class,
                () -> platoService.crearPlato(platoMock, 99L));

        assertTrue(ex.getMessage().contains("No eres el propietario"));
        verify(platoRepositoryPort, never()).guardar(any());
    }

    @Test
    void crearPlato_LanzaExcepcion_CuandoPrecioEsMenorOIgualACero() {
        platoMock.setPrecio(0);

        assertThrows(DomainException.class,
                () -> platoService.crearPlato(platoMock, 2L));
        verify(platoRepositoryPort, never()).guardar(any());
    }

    // --- TESTS HU-04: MODIFICAR PLATO ---

    @Test
    void modificarPlato_Exitoso_ActualizaPrecioYDescripcion() {
        when(platoRepositoryPort.obtenerPorId(10L)).thenReturn(Optional.of(platoMock));
        when(restauranteRepositoryPort.obtenerPorId(1L)).thenReturn(Optional.of(restauranteMock));

        platoService.modificarPlato(10L, 30000, "Nueva descripción", 2L);

        assertEquals(30000, platoMock.getPrecio());
        assertEquals("Nueva descripción", platoMock.getDescripcion());
        verify(platoRepositoryPort, times(1)).guardar(platoMock);
    }

    // --- TESTS HU-07: HABILITAR / DESHABILITAR PLATO ---

    @Test
    void cambiarEstadoPlato_Exitoso_ActualizaEstadoActivo() {
        when(platoRepositoryPort.obtenerPorId(10L)).thenReturn(Optional.of(platoMock));
        when(restauranteRepositoryPort.obtenerPorId(1L)).thenReturn(Optional.of(restauranteMock));

        platoService.cambiarEstadoPlato(10L, false, 2L);

        assertFalse(platoMock.getActivo());
        verify(platoRepositoryPort, times(1)).guardar(platoMock);
    }
}