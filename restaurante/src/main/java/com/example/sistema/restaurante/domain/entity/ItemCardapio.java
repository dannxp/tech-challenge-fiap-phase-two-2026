package com.example.sistema.restaurante.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Table("ITEM_CARDAPIO")
public class ItemCardapio {

    @Id
    private Long id;
    @Column("RESTAURANTE_ID")
    private Long restauranteId;
    @Column("NOME")
    private String nome;
    @Column("DESCRICAO")
    private String descricao;
    @Column("PRECO")
    private BigDecimal preco;
    @Column("DISPONIVEL_APENAS_LOCAL")
    private Boolean disponivelApenasLocal;
    @Column("FOTO_PATH")
    private String fotoPath;

    public ItemCardapio() {}

    public ItemCardapio(Long restauranteId, String nome, String descricao,
                        BigDecimal preco, Boolean disponivelApenasLocal, String fotoPath) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponivelApenasLocal = disponivelApenasLocal;
        this.fotoPath = fotoPath;
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
