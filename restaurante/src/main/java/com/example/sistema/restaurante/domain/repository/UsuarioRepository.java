package com.example.sistema.restaurante.domain.repository;

import com.example.sistema.restaurante.domain.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
