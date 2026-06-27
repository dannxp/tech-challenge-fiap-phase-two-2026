package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.RestauranteRequest;
import com.example.sistema.restaurante.application.dto.RestauranteResponse;
import com.example.sistema.restaurante.domain.entity.Restaurante;
import com.example.sistema.restaurante.domain.repository.RestauranteRepository;
import com.example.sistema.restaurante.domain.repository.UsuarioRepository;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RestauranteResponse criar(RestauranteRequest request) {
        usuarioRepository.findById(request.getDonoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário dono não encontrado: " + request.getDonoId()));

        Restaurante entity = new Restaurante(
                request.getNome(), request.getEndereco(), request.getTipoCozinha(),
                request.getHorarioFuncionamento(), request.getDonoId());
        entity = restauranteRepository.save(entity);
        return RestauranteResponse.fromEntity(entity);
    }

    public RestauranteResponse buscarPorId(Long id) {
        Restaurante entity = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));
        return RestauranteResponse.fromEntity(entity);
    }

    public List<RestauranteResponse> listarTodos() {
        return StreamSupport.stream(restauranteRepository.findAll().spliterator(), false)
                .map(RestauranteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public RestauranteResponse atualizar(Long id, RestauranteRequest request) {
        Restaurante entity = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));

        usuarioRepository.findById(request.getDonoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário dono não encontrado: " + request.getDonoId()));

        entity.setNome(request.getNome());
        entity.setEndereco(request.getEndereco());
        entity.setTipoCozinha(request.getTipoCozinha());
        entity.setHorarioFuncionamento(request.getHorarioFuncionamento());
        entity.setDonoId(request.getDonoId());
        entity = restauranteRepository.save(entity);
        return RestauranteResponse.fromEntity(entity);
    }

    public void deletar(Long id) {
        if (!restauranteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurante não encontrado: " + id);
        }
        restauranteRepository.deleteById(id);
    }
}
