package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.TipoUsuarioRequest;
import com.example.sistema.restaurante.application.dto.TipoUsuarioResponse;
import com.example.sistema.restaurante.domain.entity.TipoUsuario;
import com.example.sistema.restaurante.domain.repository.TipoUsuarioRepository;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository repository;

    public TipoUsuarioService(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    public TipoUsuarioResponse criar(TipoUsuarioRequest request) {
        TipoUsuario entity = new TipoUsuario(request.getNome());
        entity = repository.save(entity);
        return TipoUsuarioResponse.fromEntity(entity);
    }

    public TipoUsuarioResponse buscarPorId(Long id) {
        TipoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + id));
        return TipoUsuarioResponse.fromEntity(entity);
    }

    public List<TipoUsuarioResponse> listarTodos() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(TipoUsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TipoUsuarioResponse atualizar(Long id, TipoUsuarioRequest request) {
        TipoUsuario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + id));
        entity.setNome(request.getNome());
        entity = repository.save(entity);
        return TipoUsuarioResponse.fromEntity(entity);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de usuário não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
