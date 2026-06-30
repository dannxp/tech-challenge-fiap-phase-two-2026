package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.ItemCardapioRequest;
import com.example.sistema.restaurante.application.dto.ItemCardapioResponse;
import com.example.sistema.restaurante.application.service.ItemCardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "Itens do Cardápio", description = "CRUD de itens do cardápio")
public class ItemCardapioController {

    private final ItemCardapioService service;

    public ItemCardapioController(ItemCardapioService service) {
        this.service = service;
    }

    @PostMapping("/api/restaurantes/{restauranteId}/itens")
    @Operation(summary = "Criar item do cardápio")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Item criado"),
                   @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                   @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")})
    public ResponseEntity<ItemCardapioResponse> criar(
            @PathVariable Long restauranteId,
            @Valid @RequestBody ItemCardapioRequest request) {
        ItemCardapioResponse response = service.criar(restauranteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/restaurantes/{restauranteId}/itens")
    @Operation(summary = "Listar itens de um restaurante")
    public ResponseEntity<List<ItemCardapioResponse>> listarPorRestaurante(
            @PathVariable Long restauranteId) {
        List<ItemCardapioResponse> response = service.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/itens-cardapio/{id}")
    @Operation(summary = "Buscar item do cardápio por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Item encontrado"),
                   @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    public ResponseEntity<ItemCardapioResponse> buscarPorId(@PathVariable Long id) {
        ItemCardapioResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/itens-cardapio")
    @Operation(summary = "Listar todos os itens do cardápio")
    public ResponseEntity<List<ItemCardapioResponse>> listarTodos() {
        List<ItemCardapioResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/itens-cardapio/{id}")
    @Operation(summary = "Atualizar item do cardápio")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Item atualizado"),
                   @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    public ResponseEntity<ItemCardapioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemCardapioRequest request) {
        ItemCardapioResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/itens-cardapio/{id}")
    @Operation(summary = "Deletar item do cardápio")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Item deletado"),
                   @ApiResponse(responseCode = "404", description = "Item não encontrado")})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
