package org.grupo1.gestordereceitas.service;

import org.grupo1.gestordereceitas.dto.ReceitaIngredienteDTO;
import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.exception.BusinessException;
import org.grupo1.gestordereceitas.exception.ResourceNotFoundException;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.repository.CategoriaRepository;
import org.grupo1.gestordereceitas.repository.IngredienteRepository;
import org.grupo1.gestordereceitas.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private ReceitaService receitaService;

    private Receita receita;
    private Categoria categoria;
    private Ingrediente ingrediente;
    private ReceitaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Setup categoria
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Sobremesas");

        // Setup ingrediente
        ingrediente = new Ingrediente();
        ingrediente.setId(1L);
        ingrediente.setNome("Açúcar");

        // Setup receita
        receita = new Receita();
        receita.setId(1L);
        receita.setNome("Pudim");
        receita.setDescricao("Pudim de leite condensado");
        receita.setTempoDePreparo(60);
        receita.setCategoria(categoria);
        receita.setReceitaIngredientes(new ArrayList<>());

        // Setup DTO
        requestDTO = new ReceitaRequestDTO();
        requestDTO.setNome("Pudim");
        requestDTO.setDescricao("Pudim de leite condensado");
        requestDTO.setTempoDePreparo(60);
        requestDTO.setCategoriaId(1L);
        
        ReceitaIngredienteDTO ingredienteDTO = new ReceitaIngredienteDTO();
        ingredienteDTO.setIngredienteId(1L);
        ingredienteDTO.setQuantidade("1");
        ingredienteDTO.setUnidade("xícara");
        requestDTO.setIngredientes(Arrays.asList(ingredienteDTO));
    }

    @Test
    void listarTodas_DeveRetornarListaDeReceitas() {
        // Arrange
        List<Receita> receitas = Arrays.asList(receita);
        when(receitaRepository.findAll()).thenReturn(receitas);

        // Act
        List<ReceitaResponseDTO> resultado = receitaService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pudim", resultado.get(0).getNome());
        verify(receitaRepository).findAll();
    }

    @Test
    void buscarPorId_QuandoReceitaExiste_DeveRetornarReceita() {
        // Arrange
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receita));

        // Act
        ReceitaResponseDTO resultado = receitaService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pudim", resultado.getNome());
        assertEquals("Sobremesas", resultado.getCategoria());
        verify(receitaRepository).findById(1L);
    }

    @Test
    void buscarPorId_QuandoReceitaNaoExiste_DeveLancarException() {
        // Arrange
        when(receitaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            receitaService.buscarPorId(1L);
        });
        verify(receitaRepository).findById(1L);
    }

    @Test
    void salvar_QuandoDadosValidos_DeveRetornarReceitaSalva() {
        // Arrange
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(ingredienteRepository.findAll()).thenReturn(Arrays.asList(ingrediente));
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        // Act
        ReceitaResponseDTO resultado = receitaService.salvar(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pudim", resultado.getNome());
        assertEquals("Sobremesas", resultado.getCategoria());
        verify(categoriaRepository).findById(1L);
        verify(ingredienteRepository).findAll();
        verify(receitaRepository).save(any(Receita.class));
    }

    @Test
    void salvar_QuandoCategoriaNaoExiste_DeveLancarException() {
        // Arrange
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            receitaService.salvar(requestDTO);
        });
        verify(categoriaRepository).findById(1L);
        verify(receitaRepository, never()).save(any(Receita.class));
    }

    @Test
    void atualizar_QuandoReceitaExiste_DeveRetornarReceitaAtualizada() {
        // Arrange
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receita));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(ingredienteRepository.findAll()).thenReturn(Arrays.asList(ingrediente));
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        // Act
        ReceitaResponseDTO resultado = receitaService.atualizar(1L, requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pudim", resultado.getNome());
        verify(receitaRepository).findById(1L);
        verify(categoriaRepository).findById(1L);
        verify(receitaRepository).save(any(Receita.class));
    }

    @Test
    void atualizarParcial_QuandoReceitaExiste_DeveRetornarReceitaAtualizada() {
        // Arrange
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receita));
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        ReceitaRequestDTO atualizacaoParcial = new ReceitaRequestDTO();
        atualizacaoParcial.setNome("Pudim Atualizado");

        // Act
        ReceitaResponseDTO resultado = receitaService.atualizarParcial(1L, atualizacaoParcial);

        // Assert
        assertNotNull(resultado);
        verify(receitaRepository).findById(1L);
        verify(receitaRepository).save(any(Receita.class));
    }

    @Test
    void deletar_QuandoReceitaExiste_DeveDeletarComSucesso() {
        // Arrange
        when(receitaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(receitaRepository).deleteById(1L);

        // Act
        receitaService.deletar(1L);

        // Assert
        verify(receitaRepository).existsById(1L);
        verify(receitaRepository).deleteById(1L);
    }

    @Test
    void deletar_QuandoReceitaNaoExiste_DeveLancarException() {
        // Arrange
        when(receitaRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            receitaService.deletar(1L);
        });
        verify(receitaRepository).existsById(1L);
        verify(receitaRepository, never()).deleteById(1L);
    }
}