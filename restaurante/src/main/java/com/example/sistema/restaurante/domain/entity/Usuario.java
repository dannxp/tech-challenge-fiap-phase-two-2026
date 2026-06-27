package com.example.sistema.restaurante.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USUARIO")
public class Usuario {

    @Id
    private Long id;
    @Column("NOME")
    private String nome;
    @Column("EMAIL")
    private String email;
    @Column("TIPO_USUARIO_ID")
    private Long tipoUsuarioId;

    public Usuario() {}

    public Usuario(String nome, String email, Long tipoUsuarioId) {
        this.nome = nome;
        this.email = email;
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(Long tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }
}
