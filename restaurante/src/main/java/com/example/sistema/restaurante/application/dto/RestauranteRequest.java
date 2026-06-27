package com.example.sistema.restaurante.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RestauranteRequest {

    @NotBlank(message = "Nome do restaurante é obrigatório")
    private String nome;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "Tipo de cozinha é obrigatório")
    private String tipoCozinha;

    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioFuncionamento;

    @NotNull(message = "Dono do restaurante é obrigatório")
    private Long donoId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }
}
