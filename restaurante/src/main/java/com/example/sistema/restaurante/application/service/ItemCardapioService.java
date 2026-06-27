package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.ItemCardapioRequest;
import com.example.sistema.restaurante.application.dto.ItemCardapioResponse;
import com.example.sistema.restaurante.domain.entity.ItemCardapio;
import com.example.sistema.restaurante.domain.repository.ItemCardapioRepository;
import com.example.sistema.restaurante.domain.repository.RestauranteRepository;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepository restauranteRepository;

    public ItemCardapioService(ItemCardapioRepository itemCardapioRepository,
                               RestauranteRepository restauranteRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public ItemCardapioResponse criar(Long restauranteId, ItemCardapioRequest request) {
        restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado: " + restauranteId));

        ItemCardapio entity = new ItemCardapio(
                restauranteId, request.getNome(), request.getDescricao(),
                request.getPreco(), request.getDisponivelApenasLocal(), request.getFotoPath());
        entity = itemCardapioRepository.save(entity);
        return ItemCardapioResponse.fromEntity(entity);
    }

    public ItemCardapioResponse buscarPorId(Long id) {
        ItemCardapio entity = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado: " + id));
        return ItemCardapioResponse.fromEntity(entity);
    }

    public List<ItemCardapioResponse> listarPorRestaurante(Long restauranteId) {
        restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado: " + restauranteId));

        return itemCardapioRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(ItemCardapioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ItemCardapioResponse> listarTodos() {
        return StreamSupport.stream(itemCardapioRepository.findAll().spliterator(), false)
                .map(ItemCardapioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ItemCardapioResponse atualizar(Long id, ItemCardapioRequest request) {
        ItemCardapio entity = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado: " + id));

        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());
        entity.setPreco(request.getPreco());
        entity.setDisponivelApenasLocal(request.getDisponivelApenasLocal());
        entity.setFotoPath(request.getFotoPath());
        entity = itemCardapioRepository.save(entity);
        return ItemCardapioResponse.fromEntity(entity);
    }

    public void deletar(Long id) {
        if (!itemCardapioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item do cardápio não encontrado: " + id);
        }
        itemCardapioRepository.deleteById(id);
    }
}
