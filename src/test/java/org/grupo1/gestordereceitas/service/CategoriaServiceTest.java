package org.grupo1.gestordereceitas.service;

import org.grupo1.gestordereceitas.exception.ResourceNotFoundException;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.repository.CategoriaRepository;
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
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Sobremesas");
    }

    @Test
    void listarTodas_DeveRetornarListaDeCategorias() {
        // Arrange
        List<Categoria> categorias = Arrays.asList(
            categoria,
            createCategoria(2L, "Massas")
        );
        when(categoriaRepository.findAll()).thenReturn(categorias);

        // Act
        List<Categoria> resultado = categoriaService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Sobremesas", resultado.get(0).getNome());
        assertEquals("Massas", resultado.get(1).getNome());
        verify(categoriaRepository).findAll();
    }

    @Test
    void buscarPorId_QuandoCategoriaExiste_DeveRetornarCategoria() {
        // Arrange
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        // Act
        Categoria resultado = categoriaService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sobremesas", resultado.getNome());
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void buscarPorId_QuandoCategoriaNaoExiste_DeveLancarException() {
        // Arrange
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.buscarPorId(1L);
        });
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void salvar_DeveRetornarCategoriaSalva() {
        // Arrange
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Act
        Categoria resultado = categoriaService.salvar(categoria);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sobremesas", resultado.getNome());
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void atualizar_QuandoCategoriaExiste_DeveRetornarCategoriaAtualizada() {
        // Arrange
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome("Doces");
        
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Act
        Categoria resultado = categoriaService.atualizar(1L, categoriaAtualizada);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void atualizar_QuandoCategoriaNaoExiste_DeveLancarException() {
        // Arrange
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome("Doces");
        
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.atualizar(1L, categoriaAtualizada);
        });
        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deletar_QuandoCategoriaExiste_DeveDeletarComSucesso() {
        // Arrange
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(1L);

        // Act
        categoriaService.deletar(1L);

        // Assert
        verify(categoriaRepository).existsById(1L);
        verify(categoriaRepository).deleteById(1L);
    }

    @Test
    void deletar_QuandoCategoriaNaoExiste_DeveLancarException() {
        // Arrange
        when(categoriaRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.deletar(1L);
        });
        verify(categoriaRepository).existsById(1L);
        verify(categoriaRepository, never()).deleteById(anyLong());
    }

    private Categoria createCategoria(Long id, String nome) {
        Categoria cat = new Categoria();
        cat.setId(id);
        cat.setNome(nome);
        return cat;
    }
}