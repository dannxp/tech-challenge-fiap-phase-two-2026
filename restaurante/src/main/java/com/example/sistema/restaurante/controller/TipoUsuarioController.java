package com.example.sistema.restaurante.controller;

import com.example.sistema.restaurante.application.dto.TipoUsuarioRequest;
import com.example.sistema.restaurante.application.dto.TipoUsuarioResponse;
import com.example.sistema.restaurante.application.service.TipoUsuarioService;
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
@RequestMapping("/api/tipos-usuario")
@Tag(name = "Tipos de Usuário", description = "CRUD de tipos de usuário")
public class TipoUsuarioController {

    private final TipoUsuarioService service;

    public TipoUsuarioController(TipoUsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar tipo de usuário")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Tipo de usuário criado"),
                   @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    public ResponseEntity<TipoUsuarioResponse> criar(@Valid @RequestBody TipoUsuarioRequest request) {
        TipoUsuarioResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado"),
                   @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")})
    public ResponseEntity<TipoUsuarioResponse> buscarPorId(@PathVariable Long id) {
        TipoUsuarioResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuário")
    public ResponseEntity<List<TipoUsuarioResponse>> listarTodos() {
        List<TipoUsuarioResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado"),
                   @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")})
    public ResponseEntity<TipoUsuarioResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody TipoUsuarioRequest request) {
        TipoUsuarioResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tipo de usuário")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Tipo de usuário deletado"),
                   @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
