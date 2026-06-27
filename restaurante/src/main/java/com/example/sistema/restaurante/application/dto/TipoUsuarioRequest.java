package com.example.sistema.restaurante.application.dto;

import jakarta.validation.constraints.NotBlank;

public class TipoUsuarioRequest {

    @NotBlank(message = "Nome do tipo de usuário é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
