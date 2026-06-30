package com.example.sistema.restaurante.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("usuario")
public class Usuario {

    @Id
    private Long id;
    @Column("nome")
    private String nome;
    @Column("email")
    private String email;
    @Column("tipo_usuario_id")
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
