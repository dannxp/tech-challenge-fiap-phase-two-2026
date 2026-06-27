package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.RestauranteRequest;
import com.example.sistema.restaurante.application.dto.RestauranteResponse;
import com.example.sistema.restaurante.application.service.RestauranteService;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestauranteController.class)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestauranteService service;

    @Test
    void criar_deveRetornar201() throws Exception {
        RestauranteResponse response = new RestauranteResponse(1L, "Pizza do João",
                "Rua A, 123", "Italiana", "18h-23h", 1L);

        when(service.criar(any(RestauranteRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Pizza do João\",\"endereco\":\"Rua A, 123\"," +
                                "\"tipoCozinha\":\"Italiana\",\"horarioFuncionamento\":\"18h-23h\"," +
                                "\"donoId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pizza do João"));
    }

    @Test
    void criar_comDadosInvalidos_deveRetornar400() throws Exception {
        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"endereco\":\"\",\"tipoCozinha\":\"\"," +
                                "\"horarioFuncionamento\":\"\",\"donoId\":null}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarPorId_deveRetornar200() throws Exception {
        RestauranteResponse response = new RestauranteResponse(1L, "Bar do Zé",
                "Rua B, 456", "Brasileira", "11h-23h", 1L);

        when(service.buscarPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/restaurantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Bar do Zé"));
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveRetornar404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Restaurante não encontrado: 99"));

        mockMvc.perform(get("/api/restaurantes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarTodos_deveRetornar200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(
                new RestauranteResponse(1L, "R1", "End1", "Tipo1", "Horário1", 1L)
        ));

        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void atualizar_deveRetornar200() throws Exception {
        RestauranteResponse response = new RestauranteResponse(1L, "R1 Atualizado",
                "End Novo", "Tipo Novo", "Horário Novo", 1L);

        when(service.atualizar(eq(1L), any(RestauranteRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/restaurantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"R1 Atualizado\",\"endereco\":\"End Novo\"," +
                                "\"tipoCozinha\":\"Tipo Novo\",\"horarioFuncionamento\":\"Horário Novo\"," +
                                "\"donoId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("R1 Atualizado"));
    }

    @Test
    void deletar_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/restaurantes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_quandoNaoExistir_deveRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("Restaurante não encontrado: 99"))
                .when(service).deletar(99L);

        mockMvc.perform(delete("/api/restaurantes/99"))
                .andExpect(status().isNotFound());
    }
}
