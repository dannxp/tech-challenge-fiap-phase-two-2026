package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.ItemCardapioRequest;
import com.example.sistema.restaurante.application.dto.ItemCardapioResponse;
import com.example.sistema.restaurante.domain.entity.ItemCardapio;
import com.example.sistema.restaurante.domain.entity.Restaurante;
import com.example.sistema.restaurante.domain.repository.ItemCardapioRepository;
import com.example.sistema.restaurante.domain.repository.RestauranteRepository;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioServiceTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    private ItemCardapioService service;

    @BeforeEach
    void setUp() {
        service = new ItemCardapioService(itemCardapioRepository, restauranteRepository);
    }

    @Test
    void criar_quandoRestauranteExistir_deveSalvar() {
        Restaurante restaurante = new Restaurante("R1", "End", "Tipo", "Horário", 1L);
        restaurante.setId(1L);

        ItemCardapioRequest request = new ItemCardapioRequest();
        request.setNome("Pizza Margherita");
        request.setDescricao("Mussarela, tomate e manjericão");
        request.setPreco(new BigDecimal("45.00"));
        request.setDisponivelApenasLocal(true);
        request.setFotoPath("/fotos/pizza.jpg");

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));

        ItemCardapio entity = new ItemCardapio(1L, "Pizza Margherita", "Mussarela, tomate e manjericão",
                new BigDecimal("45.00"), true, "/fotos/pizza.jpg");
        entity.setId(1L);
        when(itemCardapioRepository.save(any(ItemCardapio.class))).thenReturn(entity);

        ItemCardapioResponse response = service.criar(1L, request);

        assertNotNull(response);
        assertEquals("Pizza Margherita", response.getNome());
        assertEquals(new BigDecimal("45.00"), response.getPreco());
        assertTrue(response.getDisponivelApenasLocal());
    }

    @Test
    void criar_quandoRestauranteNaoExistir_deveLancarExcecao() {
        when(restauranteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.criar(99L, new ItemCardapioRequest()));
    }

    @Test
    void buscarPorId_quandoExistir_deveRetornar() {
        ItemCardapio entity = new ItemCardapio(1L, "Lasanha", "Lasagna a bolonhesa",
                new BigDecimal("55.00"), false, null);
        entity.setId(1L);

        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.of(entity));

        ItemCardapioResponse response = service.buscarPorId(1L);

        assertEquals("Lasanha", response.getNome());
        assertEquals(1L, response.getRestauranteId());
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveLancarExcecao() {
        when(itemCardapioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void listarPorRestaurante_quandoExistir_deveRetornarItens() {
        Restaurante restaurante = new Restaurante("R1", "End", "Tipo", "Horário", 1L);
        restaurante.setId(1L);

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));

        ItemCardapio i1 = new ItemCardapio(1L, "Item1", "Desc1", BigDecimal.TEN, false, null);
        i1.setId(1L);
        ItemCardapio i2 = new ItemCardapio(1L, "Item2", "Desc2", BigDecimal.ONE, true, null);
        i2.setId(2L);

        when(itemCardapioRepository.findByRestauranteId(1L)).thenReturn(List.of(i1, i2));

        List<ItemCardapioResponse> response = service.listarPorRestaurante(1L);

        assertEquals(2, response.size());
    }

    @Test
    void listarPorRestaurante_quandoNaoExistir_deveLancarExcecao() {
        when(restauranteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.listarPorRestaurante(99L));
    }

    @Test
    void atualizar_quandoExistir_deveAtualizar() {
        ItemCardapio entity = new ItemCardapio(1L, "Nome Antigo", "Desc Antiga",
                BigDecimal.TEN, false, null);
        entity.setId(1L);

        ItemCardapioRequest request = new ItemCardapioRequest();
        request.setNome("Nome Novo");
        request.setDescricao("Desc Nova");
        request.setPreco(new BigDecimal("30.00"));
        request.setDisponivelApenasLocal(true);
        request.setFotoPath("/nova.jpg");

        when(itemCardapioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(itemCardapioRepository.save(any(ItemCardapio.class))).thenReturn(entity);

        ItemCardapioResponse response = service.atualizar(1L, request);

        assertEquals("Nome Novo", response.getNome());
        assertEquals(new BigDecimal("30.00"), response.getPreco());
        assertTrue(response.getDisponivelApenasLocal());
    }

    @Test
    void deletar_quandoExistir_deveRemover() {
        when(itemCardapioRepository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(itemCardapioRepository).deleteById(1L);
    }

    @Test
    void deletar_quandoNaoExistir_deveLancarExcecao() {
        when(itemCardapioRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }
}
