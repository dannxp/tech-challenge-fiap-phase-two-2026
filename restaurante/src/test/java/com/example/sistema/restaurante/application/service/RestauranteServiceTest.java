package com.example.sistema.restaurante.application.service;

import com.example.sistema.restaurante.application.dto.RestauranteRequest;
import com.example.sistema.restaurante.application.dto.RestauranteResponse;
import com.example.sistema.restaurante.domain.entity.Restaurante;
import com.example.sistema.restaurante.domain.entity.Usuario;
import com.example.sistema.restaurante.domain.repository.RestauranteRepository;
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
class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private RestauranteService service;

    @BeforeEach
    void setUp() {
        service = new RestauranteService(restauranteRepository, usuarioRepository);
    }

    @Test
    void criar_quandoDonoExistir_deveSalvar() {
        Usuario dono = new Usuario("João", "joao@email.com", 1L);
        dono.setId(1L);

        RestauranteRequest request = new RestauranteRequest();
        request.setNome("Pizza do João");
        request.setEndereco("Rua A, 123");
        request.setTipoCozinha("Italiana");
        request.setHorarioFuncionamento("18h-23h");
        request.setDonoId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(dono));

        Restaurante entity = new Restaurante("Pizza do João", "Rua A, 123", "Italiana", "18h-23h", 1L);
        entity.setId(1L);
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(entity);

        RestauranteResponse response = service.criar(request);

        assertNotNull(response);
        assertEquals("Pizza do João", response.getNome());
        assertEquals(1L, response.getDonoId());
    }

    @Test
    void criar_quandoDonoNaoExistir_deveLancarExcecao() {
        RestauranteRequest request = new RestauranteRequest();
        request.setDonoId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.criar(request));
    }

    @Test
    void buscarPorId_quandoExistir_deveRetornar() {
        Restaurante entity = new Restaurante("Bar do Zé", "Rua B, 456", "Brasileira", "11h-23h", 1L);
        entity.setId(1L);

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(entity));

        RestauranteResponse response = service.buscarPorId(1L);

        assertEquals("Bar do Zé", response.getNome());
    }

    @Test
    void buscarPorId_quandoNaoExistir_deveLancarExcecao() {
        when(restauranteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void listarTodos_deveRetornarLista() {
        Restaurante r1 = new Restaurante("R1", "End1", "Tipo1", "Horário1", 1L);
        r1.setId(1L);
        Restaurante r2 = new Restaurante("R2", "End2", "Tipo2", "Horário2", 2L);
        r2.setId(2L);

        when(restauranteRepository.findAll()).thenReturn(List.of(r1, r2));

        List<RestauranteResponse> response = service.listarTodos();

        assertEquals(2, response.size());
    }

    @Test
    void atualizar_quandoValido_deveAtualizar() {
        Restaurante entity = new Restaurante("Nome Antigo", "End Antigo", "Tipo", "Horário", 1L);
        entity.setId(1L);

        RestauranteRequest request = new RestauranteRequest();
        request.setNome("Nome Novo");
        request.setEndereco("End Novo");
        request.setTipoCozinha("Tipo Novo");
        request.setHorarioFuncionamento("Horário Novo");
        request.setDonoId(1L);

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario("João", "joao@email.com", 1L)));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(entity);

        RestauranteResponse response = service.atualizar(1L, request);

        assertEquals("Nome Novo", response.getNome());
    }

    @Test
    void deletar_quandoExistir_deveRemover() {
        when(restauranteRepository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(restauranteRepository).deleteById(1L);
    }

    @Test
    void deletar_quandoNaoExistir_deveLancarExcecao() {
        when(restauranteRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }
}
