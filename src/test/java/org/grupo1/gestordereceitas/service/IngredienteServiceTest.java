package org.grupo1.gestordereceitas.service;

import org.grupo1.gestordereceitas.exception.ResourceNotFoundException;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.repository.IngredienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private IngredienteService ingredienteService;

    private Ingrediente ingrediente;

    @BeforeEach
    void setUp() {
        ingrediente = new Ingrediente();
        ingrediente.setId(1L);
        ingrediente.setNome("Açúcar");
    }

    @Test
    void listarTodos_DeveRetornarListaDeIngredientes() {
        // Arrange
        List<Ingrediente> ingredientes = Arrays.asList(
            ingrediente,
            createIngrediente(2L, "Sal")
        );
        when(ingredienteRepository.findAll()).thenReturn(ingredientes);

        // Act
        List<Ingrediente> resultado = ingredienteService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Açúcar", resultado.get(0).getNome());
        assertEquals("Sal", resultado.get(1).getNome());
        verify(ingredienteRepository).findAll();
    }

    @Test
    void buscarPorId_QuandoIngredienteExiste_DeveRetornarIngrediente() {
        // Arrange
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(ingrediente));

        // Act
        Ingrediente resultado = ingredienteService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Açúcar", resultado.getNome());
        verify(ingredienteRepository).findById(1L);
    }

    @Test
    void buscarPorId_QuandoIngredienteNaoExiste_DeveLancarException() {
        // Arrange
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredienteService.buscarPorId(1L);
        });
        verify(ingredienteRepository).findById(1L);
    }

    @Test
    void salvar_DeveRetornarIngredienteSalvo() {
        // Arrange
        when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingrediente);

        // Act
        Ingrediente resultado = ingredienteService.salvar(ingrediente);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Açúcar", resultado.getNome());
        verify(ingredienteRepository).save(ingrediente);
    }

    @Test
    void atualizar_QuandoIngredienteExiste_DeveRetornarIngredienteAtualizado() {
        // Arrange
        Ingrediente ingredienteAtualizado = new Ingrediente();
        ingredienteAtualizado.setNome("Açúcar Refinado");
        
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(ingrediente));
        when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingrediente);

        // Act
        Ingrediente resultado = ingredienteService.atualizar(1L, ingredienteAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(ingredienteRepository).findById(1L);
        verify(ingredienteRepository).save(any(Ingrediente.class));
    }

    @Test
    void atualizar_QuandoIngredienteNaoExiste_DeveLancarException() {
        // Arrange
        Ingrediente ingredienteAtualizado = new Ingrediente();
        ingredienteAtualizado.setNome("Açúcar Refinado");
        
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredienteService.atualizar(1L, ingredienteAtualizado);
        });
        verify(ingredienteRepository).findById(1L);
        verify(ingredienteRepository, never()).save(any(Ingrediente.class));
    }

    @Test
    void deletar_QuandoIngredienteExiste_DeveDeletarComSucesso() {
        // Arrange
        when(ingredienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ingredienteRepository).deleteById(1L);

        // Act
        ingredienteService.deletar(1L);

        // Assert
        verify(ingredienteRepository).existsById(1L);
        verify(ingredienteRepository).deleteById(1L);
    }

    @Test
    void deletar_QuandoIngredienteNaoExiste_DeveLancarException() {
        // Arrange
        when(ingredienteRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredienteService.deletar(1L);
        });
        verify(ingredienteRepository).existsById(1L);
        verify(ingredienteRepository, never()).deleteById(anyLong());
    }

    private Ingrediente createIngrediente(Long id, String nome) {
        Ingrediente ing = new Ingrediente();
        ing.setId(id);
        ing.setNome(nome);
        return ing;
    }
}