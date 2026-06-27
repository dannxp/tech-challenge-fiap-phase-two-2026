package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.TipoUsuarioRequest;
import com.example.sistema.restaurante.application.dto.TipoUsuarioResponse;
import com.example.sistema.restaurante.application.service.TipoUsuarioService;
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

@WebMvcTest(TipoUsuarioController.class)
class TipoUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TipoUsuarioService service;

    @Test
    void criar_deveRetornar201() throws Exception {
        TipoUsuarioResponse response = new TipoUsuarioResponse(1L, "Dono");

        when(service.criar(any(TipoUsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Dono\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Dono"));
    }

    @Test
    void criar_comNomeVazio_deveRetornar400() throws Exception {
        mockMvc.perform(post("/api/tipos-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarPorId_deveRetornar200() throws Exception {
        TipoUsuarioResponse response = new TipoUsuarioResponse(1L, "Cliente");

        when(service.buscarPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/tipos-usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Cliente"));
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveRetornar404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Tipo de usuário não encontrado: 99"));

        mockMvc.perform(get("/api/tipos-usuario/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarTodos_deveRetornar200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(
                new TipoUsuarioResponse(1L, "Dono"),
                new TipoUsuarioResponse(2L, "Cliente")
        ));

        mockMvc.perform(get("/api/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void atualizar_deveRetornar200() throws Exception {
        TipoUsuarioResponse response = new TipoUsuarioResponse(1L, "Cliente VIP");

        when(service.atualizar(eq(1L), any(TipoUsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/tipos-usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Cliente VIP\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente VIP"));
    }

    @Test
    void deletar_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/tipos-usuario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_quandoNaoExistir_deveRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("Tipo de usuário não encontrado: 99"))
                .when(service).deletar(99L);

        mockMvc.perform(delete("/api/tipos-usuario/99"))
                .andExpect(status().isNotFound());
    }
}
