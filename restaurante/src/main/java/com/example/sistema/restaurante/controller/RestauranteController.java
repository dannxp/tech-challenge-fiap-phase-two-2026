package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.RestauranteRequest;
import com.example.sistema.restaurante.application.dto.RestauranteResponse;
import com.example.sistema.restaurante.application.service.RestauranteService;
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
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "CRUD de restaurantes")
public class RestauranteController {

    private final RestauranteService service;

    public RestauranteController(RestauranteService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar restaurante")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Restaurante criado"),
                   @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<RestauranteResponse> criar(@Valid @RequestBody RestauranteRequest request) {
        RestauranteResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
                   @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")})
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        RestauranteResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os restaurantes")
    public ResponseEntity<List<RestauranteResponse>> listarTodos() {
        List<RestauranteResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
                   @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")})
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody RestauranteRequest request) {
        RestauranteResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar restaurante")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante deletado"),
                   @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
