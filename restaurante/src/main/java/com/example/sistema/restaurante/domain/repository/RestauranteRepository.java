package com.example.sistema.restaurante.domain.repository;

import com.example.sistema.restaurante.domain.entity.Restaurante;
import org.springframework.data.repository.CrudRepository;

public interface RestauranteRepository extends CrudRepository<Restaurante, Long> {
}
