package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.ItemCardapioRequest;
import com.example.sistema.restaurante.application.dto.ItemCardapioResponse;
import com.example.sistema.restaurante.application.service.ItemCardapioService;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemCardapioController.class)
class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemCardapioService service;

    @Test
    void criar_deveRetornar201() throws Exception {
        ItemCardapioResponse response = new ItemCardapioResponse(1L, 1L, "Pizza Margherita",
                "Mussarela, tomate e manjericão", new BigDecimal("45.00"), true, "/fotos/pizza.jpg");

        when(service.criar(eq(1L), any(ItemCardapioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/restaurantes/1/itens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Pizza Margherita\",\"descricao\":\"Mussarela, tomate e manjericão\"," +
                                "\"preco\":45.00,\"disponivelApenasLocal\":true,\"fotoPath\":\"/fotos/pizza.jpg\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"));
    }

    @Test
    void criar_comDadosInvalidos_deveRetornar400() throws Exception {
        mockMvc.perform(post("/api/restaurantes/1/itens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"preco\":-1,\"disponivelApenasLocal\":null}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarPorRestaurante_deveRetornar200() throws Exception {
        when(service.listarPorRestaurante(1L)).thenReturn(List.of(
                new ItemCardapioResponse(1L, 1L, "Item1", "Desc1",
                        BigDecimal.TEN, false, null)
        ));

        mockMvc.perform(get("/api/restaurantes/1/itens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void listarPorRestaurante_quandoNaoExistir_deveRetornar404() throws Exception {
        when(service.listarPorRestaurante(99L))
                .thenThrow(new ResourceNotFoundException("Restaurante não encontrado: 99"));

        mockMvc.perform(get("/api/restaurantes/99/itens"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorId_deveRetornar200() throws Exception {
        ItemCardapioResponse response = new ItemCardapioResponse(1L, 1L, "Lasanha",
                "Lasagna a bolonhesa", new BigDecimal("55.00"), false, null);

        when(service.buscarPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/itens-cardapio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Lasanha"));
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveRetornar404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Item do cardápio não encontrado: 99"));

        mockMvc.perform(get("/api/itens-cardapio/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarTodos_deveRetornar200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(
                new ItemCardapioResponse(1L, 1L, "Item1", "Desc1",
                        BigDecimal.TEN, false, null)
        ));

        mockMvc.perform(get("/api/itens-cardapio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void atualizar_deveRetornar200() throws Exception {
        ItemCardapioResponse response = new ItemCardapioResponse(1L, 1L, "Item Atualizado",
                "Desc Nova", new BigDecimal("30.00"), true, "/foto.jpg");

        when(service.atualizar(eq(1L), any(ItemCardapioRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/itens-cardapio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Item Atualizado\",\"descricao\":\"Desc Nova\"," +
                                "\"preco\":30.00,\"disponivelApenasLocal\":true,\"fotoPath\":\"/foto.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Item Atualizado"));
    }

    @Test
    void deletar_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/itens-cardapio/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_quandoNaoExistir_deveRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("Item do cardápio não encontrado: 99"))
                .when(service).deletar(99L);

        mockMvc.perform(delete("/api/itens-cardapio/99"))
                .andExpect(status().isNotFound());
    }
}
