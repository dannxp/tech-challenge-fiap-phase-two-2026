package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.UsuarioRequest;
import com.example.sistema.restaurante.application.dto.UsuarioResponse;
import com.example.sistema.restaurante.domain.entity.TipoUsuario;
import com.example.sistema.restaurante.domain.entity.Usuario;
import com.example.sistema.restaurante.domain.repository.TipoUsuarioRepository;
import com.example.sistema.restaurante.domain.repository.UsuarioRepository;
import com.example.sistema.restaurante.config.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    private UsuarioService service;

    @BeforeEach
    void setUp() {
        service = new UsuarioService(usuarioRepository, tipoUsuarioRepository);
    }

    @Test
    void criar_quandoTipoUsuarioExistir_deveSalvarERetornar() {
        TipoUsuario tipo = new TipoUsuario("Dono");
        tipo.setId(1L);

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("João");
        request.setEmail("joao@email.com");
        request.setTipoUsuarioId(1L);

        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(tipo));

        Usuario entity = new Usuario("João", "joao@email.com", 1L);
        entity.setId(1L);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(entity);

        UsuarioResponse response = service.criar(request);

        assertNotNull(response);
        assertEquals("João", response.getNome());
        assertEquals("joao@email.com", response.getEmail());
        assertEquals(1L, response.getTipoUsuarioId());
    }

    @Test
    void criar_quandoTipoUsuarioNaoExistir_deveLancarExcecao() {
        UsuarioRequest request = new UsuarioRequest();
        request.setTipoUsuarioId(99L);

        when(tipoUsuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.criar(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void buscarPorId_quandoExistir_deveRetornar() {
        Usuario entity = new Usuario("Maria", "maria@email.com", 1L);
        entity.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entity));

        UsuarioResponse response = service.buscarPorId(1L);

        assertEquals("Maria", response.getNome());
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveLancarExcecao() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void listarTodos_deveRetornarLista() {
        Usuario u1 = new Usuario("Ana", "ana@email.com", 1L);
        u1.setId(1L);
        Usuario u2 = new Usuario("Pedro", "pedro@email.com", 2L);
        u2.setId(2L);

        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2));

        List<UsuarioResponse> response = service.listarTodos();

        assertEquals(2, response.size());
    }

    @Test
    void atualizar_quandoTudoValido_deveAtualizar() {
        Usuario entity = new Usuario("João", "joao@email.com", 1L);
        entity.setId(1L);

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("João Silva");
        request.setEmail("joao.silva@email.com");
        request.setTipoUsuarioId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(new TipoUsuario("Dono")));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(entity);

        UsuarioResponse response = service.atualizar(1L, request);

        assertEquals("João Silva", response.getNome());
    }

    @Test
    void deletar_quandoExistir_deveRemover() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deletar_quandoNaoExistir_deveLancarExcecao() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }
}
