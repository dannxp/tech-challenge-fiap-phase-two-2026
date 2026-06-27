package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.UsuarioRequest;
import com.example.sistema.restaurante.application.dto.UsuarioResponse;
import com.example.sistema.restaurante.domain.entity.Usuario;
import com.example.sistema.restaurante.domain.repository.TipoUsuarioRepository;
import com.example.sistema.restaurante.domain.repository.UsuarioRepository;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    public UsuarioResponse criar(UsuarioRequest request) {
        tipoUsuarioRepository.findById(request.getTipoUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de usuário não encontrado: " + request.getTipoUsuarioId()));

        Usuario entity = new Usuario(request.getNome(), request.getEmail(), request.getTipoUsuarioId());
        entity = usuarioRepository.save(entity);
        return UsuarioResponse.fromEntity(entity);
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
        return UsuarioResponse.fromEntity(entity);
    }

    public List<UsuarioResponse> listarTodos() {
        return StreamSupport.stream(usuarioRepository.findAll().spliterator(), false)
                .map(UsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));

        tipoUsuarioRepository.findById(request.getTipoUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de usuário não encontrado: " + request.getTipoUsuarioId()));

        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setTipoUsuarioId(request.getTipoUsuarioId());
        entity = usuarioRepository.save(entity);
        return UsuarioResponse.fromEntity(entity);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
