package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.TipoUsuarioRequest;
import com.example.sistema.restaurante.application.dto.TipoUsuarioResponse;
import com.example.sistema.restaurante.domain.entity.TipoUsuario;
import com.example.sistema.restaurante.domain.repository.TipoUsuarioRepository;
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
class TipoUsuarioServiceTest {

    @Mock
    private TipoUsuarioRepository repository;

    private TipoUsuarioService service;

    @BeforeEach
    void setUp() {
        service = new TipoUsuarioService(repository);
    }

    @Test
    void criar_deveSalvarERetornarResponse() {
        TipoUsuarioRequest request = new TipoUsuarioRequest();
        request.setNome("Dono de Restaurante");

        TipoUsuario entity = new TipoUsuario("Dono de Restaurante");
        entity.setId(1L);

        when(repository.save(any(TipoUsuario.class))).thenReturn(entity);

        TipoUsuarioResponse response = service.criar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Dono de Restaurante", response.getNome());
        verify(repository).save(any(TipoUsuario.class));
    }

    @Test
    void buscarPorId_quandoExistir_deveRetornarResponse() {
        TipoUsuario entity = new TipoUsuario("Cliente");
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        TipoUsuarioResponse response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Cliente", response.getNome());
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void listarTodos_deveRetornarLista() {
        TipoUsuario t1 = new TipoUsuario("Dono");
        t1.setId(1L);
        TipoUsuario t2 = new TipoUsuario("Cliente");
        t2.setId(2L);

        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<TipoUsuarioResponse> response = service.listarTodos();

        assertEquals(2, response.size());
        assertEquals("Dono", response.get(0).getNome());
        assertEquals("Cliente", response.get(1).getNome());
    }

    @Test
    void atualizar_quandoExistir_deveAtualizarERetornar() {
        TipoUsuario entity = new TipoUsuario("Cliente");
        entity.setId(1L);

        TipoUsuarioRequest request = new TipoUsuarioRequest();
        request.setNome("Cliente VIP");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(TipoUsuario.class))).thenReturn(entity);

        TipoUsuarioResponse response = service.atualizar(1L, request);

        assertNotNull(response);
        assertEquals("Cliente VIP", response.getNome());
        verify(repository).save(entity);
    }

    @Test
    void atualizar_quandoNaoExistir_deveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar(99L, new TipoUsuarioRequest()));
    }

    @Test
    void deletar_quandoExistir_deveRemover() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deletar_quandoNaoExistir_deveLancarExcecao() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
        verify(repository, never()).deleteById(any());
    }
}
