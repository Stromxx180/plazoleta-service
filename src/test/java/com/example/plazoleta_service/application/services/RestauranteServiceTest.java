package com.example.plazoleta_service.application.services;

import com.example.plazoleta_service.application.ports.output.RestauranteRepositoryPort;
import com.example.plazoleta_service.domain.exceptions.DomainException;
import com.example.plazoleta_service.domain.models.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    @Mock
    private RestauranteRepositoryPort restauranteRepositoryPort;

    @InjectMocks
    private RestauranteService restauranteService;

    private Restaurante restauranteValido;

    @BeforeEach
    void setUp() {
        restauranteValido = new Restaurante(
                null,
                "Sabor Caribeño",
                "900123456",
                "Calle 72 # 50-20",
                "+573001112233",
                "https://imagen.com/logo.png",
                2L
        );
    }

    // --- TESTS HU-02: CREAR RESTAURANTE ---

    @Test
    void crearRestaurante_Exitoso_CuandoDatosSonValidos() {
        when(restauranteRepositoryPort.existePorNit("900123456")).thenReturn(false);

        restauranteService.crearRestaurante(restauranteValido);

        verify(restauranteRepositoryPort, times(1)).guardar(restauranteValido);
    }

    @Test
    void crearRestaurante_LanzaExcepcion_CuandoNombreEsSoloNumeros() {
        restauranteValido.setNombre("123456");

        DomainException ex = assertThrows(DomainException.class,
                () -> restauranteService.crearRestaurante(restauranteValido));

        assertTrue(ex.getMessage().contains("solo números"));
        verify(restauranteRepositoryPort, never()).guardar(any());
    }

    @Test
    void crearRestaurante_LanzaExcepcion_CuandoNitNoEsNumerico() {
        restauranteValido.setNit("900ABC");

        assertThrows(DomainException.class,
                () -> restauranteService.crearRestaurante(restauranteValido));
        verify(restauranteRepositoryPort, never()).guardar(any());
    }

    @Test
    void crearRestaurante_LanzaExcepcion_CuandoNitYaExiste() {
        when(restauranteRepositoryPort.existePorNit("900123456")).thenReturn(true);

        DomainException ex = assertThrows(DomainException.class,
                () -> restauranteService.crearRestaurante(restauranteValido));

        assertTrue(ex.getMessage().contains("NIT"));
        verify(restauranteRepositoryPort, never()).guardar(any());
    }

    // --- TESTS HU-09: LISTAR RESTAURANTES ---

    @Test
    void listarRestaurantes_Exitoso_DevuelveListaPaginada() {
        when(restauranteRepositoryPort.listarPaginadosPorNombreAsc(0, 10))
                .thenReturn(List.of(restauranteValido));

        List<Restaurante> resultado = restauranteService.listarRestaurantes(0, 10);

        assertEquals(1, resultado.size());
        assertEquals("Sabor Caribeño", resultado.get(0).getNombre());
        verify(restauranteRepositoryPort, times(1)).listarPaginadosPorNombreAsc(0, 10);
    }

    @Test
    void listarRestaurantes_LanzaExcepcion_CuandoPaginacionEsInvalida() {
        assertThrows(DomainException.class,
                () -> restauranteService.listarRestaurantes(-1, 0));
    }
}