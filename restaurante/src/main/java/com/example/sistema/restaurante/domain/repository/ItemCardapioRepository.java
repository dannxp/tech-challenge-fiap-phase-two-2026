package com.example.sistema.restaurante.domain.repository;

import com.example.sistema.restaurante.domain.entity.ItemCardapio;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ItemCardapioRepository extends CrudRepository<ItemCardapio, Long> {
    List<ItemCardapio> findByRestauranteId(Long restauranteId);
}
