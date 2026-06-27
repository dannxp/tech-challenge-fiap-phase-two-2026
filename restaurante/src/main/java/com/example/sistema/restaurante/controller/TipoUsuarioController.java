package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.TipoUsuarioRequest;
import com.example.sistema.restaurante.application.dto.TipoUsuarioResponse;
import com.example.sistema.restaurante.application.service.TipoUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioService service;

    public TipoUsuarioController(TipoUsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoUsuarioResponse> criar(@Valid @RequestBody TipoUsuarioRequest request) {
        TipoUsuarioResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponse> buscarPorId(@PathVariable Long id) {
        TipoUsuarioResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuarioResponse>> listarTodos() {
        List<TipoUsuarioResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody TipoUsuarioRequest request) {
        TipoUsuarioResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
