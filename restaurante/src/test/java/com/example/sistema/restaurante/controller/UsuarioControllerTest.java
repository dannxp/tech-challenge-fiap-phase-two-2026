package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.UsuarioRequest;
import com.example.sistema.restaurante.application.dto.UsuarioResponse;
import com.example.sistema.restaurante.application.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;

    @Test
    void criar_deveRetornar201() throws Exception {
        UsuarioResponse response = new UsuarioResponse(1L, "João", "joao@email.com", 1L);

        when(service.criar(any(UsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João\",\"email\":\"joao@email.com\",\"tipoUsuarioId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void criar_comDadosInvalidos_deveRetornar400() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"email\":\"invalido\",\"tipoUsuarioId\":null}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarPorId_deveRetornar200() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(new UsuarioResponse(1L, "Maria", "maria@email.com", 1L));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("maria@email.com"));
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveRetornar404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Usuário não encontrado: 99"));

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarTodos_deveRetornar200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(
                new UsuarioResponse(1L, "João", "joao@email.com", 1L)
        ));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void atualizar_deveRetornar200() throws Exception {
        UsuarioResponse response = new UsuarioResponse(1L, "João Atualizado", "joao@email.com", 2L);

        when(service.atualizar(eq(1L), any(UsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João Atualizado\",\"email\":\"joao@email.com\",\"tipoUsuarioId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Atualizado"));
    }

    @Test
    void deletar_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletar_quandoNaoExistir_deveRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("Usuário não encontrado: 99"))
                .when(service).deletar(99L);

        mockMvc.perform(delete("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }
}
