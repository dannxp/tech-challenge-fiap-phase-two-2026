package com.example.sistema.restaurante.application.dto;

import com.example.sistema.restaurante.domain.entity.ItemCardapio;
import java.math.BigDecimal;

public class ItemCardapioResponse {

    private Long id;
    private Long restauranteId;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivelApenasLocal;
    private String fotoPath;

    public ItemCardapioResponse() {}

    public ItemCardapioResponse(Long id, Long restauranteId, String nome, String descricao,
                                BigDecimal preco, Boolean disponivelApenasLocal, String fotoPath) {
        this.id = id;
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponivelApenasLocal = disponivelApenasLocal;
        this.fotoPath = fotoPath;
    }

    public static ItemCardapioResponse fromEntity(ItemCardapio item) {
        return new ItemCardapioResponse(
                item.getId(),
                item.getRestauranteId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.getDisponivelApenasLocal(),
                item.getFotoPath()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getDisponivelApenasLocal() {
        return disponivelApenasLocal;
    }

    public void setDisponivelApenasLocal(Boolean disponivelApenasLocal) {
        this.disponivelApenasLocal = disponivelApenasLocal;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }
}
