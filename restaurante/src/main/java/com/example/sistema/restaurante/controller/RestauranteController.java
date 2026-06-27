package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.RestauranteRequest;
import com.example.sistema.restaurante.application.dto.RestauranteResponse;
import com.example.sistema.restaurante.application.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService service;

    public RestauranteController(RestauranteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponse> criar(@Valid @RequestBody RestauranteRequest request) {
        RestauranteResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        RestauranteResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RestauranteResponse>> listarTodos() {
        List<RestauranteResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody RestauranteRequest request) {
        RestauranteResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
