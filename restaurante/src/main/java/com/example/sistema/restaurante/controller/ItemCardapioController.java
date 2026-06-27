package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.ItemCardapioRequest;
import com.example.sistema.restaurante.application.dto.ItemCardapioResponse;
import com.example.sistema.restaurante.application.service.ItemCardapioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ItemCardapioController {

    private final ItemCardapioService service;

    public ItemCardapioController(ItemCardapioService service) {
        this.service = service;
    }

    @PostMapping("/api/restaurantes/{restauranteId}/itens")
    public ResponseEntity<ItemCardapioResponse> criar(
            @PathVariable Long restauranteId,
            @Valid @RequestBody ItemCardapioRequest request) {
        ItemCardapioResponse response = service.criar(restauranteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/restaurantes/{restauranteId}/itens")
    public ResponseEntity<List<ItemCardapioResponse>> listarPorRestaurante(
            @PathVariable Long restauranteId) {
        List<ItemCardapioResponse> response = service.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/itens-cardapio/{id}")
    public ResponseEntity<ItemCardapioResponse> buscarPorId(@PathVariable Long id) {
        ItemCardapioResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/itens-cardapio")
    public ResponseEntity<List<ItemCardapioResponse>> listarTodos() {
        List<ItemCardapioResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/itens-cardapio/{id}")
    public ResponseEntity<ItemCardapioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemCardapioRequest request) {
        ItemCardapioResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/itens-cardapio/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
