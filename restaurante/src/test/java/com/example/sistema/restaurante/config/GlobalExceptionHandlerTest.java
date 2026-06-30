package com.example.sistema.restaurante.config;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound() {
        var ex = new ResourceNotFoundException("Usuário não encontrado: 99");
        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Não Encontrado", response.getBody().get("error"));
        assertEquals("Usuário não encontrado: 99", response.getBody().get("message"));
    }

    @Test
    void handleValidation() {
        var target = new Object();
        var bindingResult = new BeanPropertyBindingResult(target, "target");
        bindingResult.addError(new FieldError("target", "nome", "Nome é obrigatório"));

        var ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de Validação", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("errors"));
        assertEquals("Nome é obrigatório", ((Map<String, String>) response.getBody().get("errors")).get("nome"));
    }

    @Test
    void handleDataIntegrity() {
        var ex = new DataIntegrityViolationException("violação");
        ResponseEntity<Map<String, Object>> response = handler.handleDataIntegrity(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Conflito", response.getBody().get("error"));
        assertEquals("Registro possui vínculos com outros dados e não pode ser removido.", response.getBody().get("message"));
    }

    @Test
    void handleHttpMessageNotReadable() {
        var ex = new HttpMessageNotReadableException("JSON inválido", null);
        ResponseEntity<Map<String, Object>> response = handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Requisição Inválida", response.getBody().get("error"));
        assertEquals("Corpo da requisição inválido. Verifique o formato JSON.", response.getBody().get("message"));
    }

    @Test
    void handleIllegalArgument() {
        var ex = new IllegalArgumentException("ID inválido");
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Requisição Inválida", response.getBody().get("error"));
        assertEquals("ID inválido", response.getBody().get("message"));
    }

    @Test
    void handleGeneral() {
        var ex = new RuntimeException("erro inesperado");
        ResponseEntity<Map<String, Object>> response = handler.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro Interno", response.getBody().get("error"));
        assertEquals("erro inesperado", response.getBody().get("message"));
    }
}
