package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.ReceitaIngredienteDTO;
import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.model.ReceitaIngrediente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReceitaMapperTest {

    private Categoria categoria;
    private Ingrediente ingrediente1;
    private Ingrediente ingrediente2;
    private List<Ingrediente> ingredientesDisponiveis;

    @BeforeEach
    void setUp() {
        // Setup categoria
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Sobremesas");

        // Setup ingredientes
        ingrediente1 = new Ingrediente();
        ingrediente1.setId(1L);
        ingrediente1.setNome("Açúcar");

        ingrediente2 = new Ingrediente();
        ingrediente2.setId(2L);
        ingrediente2.setNome("Leite Condensado");

        ingredientesDisponiveis = List.of(ingrediente1, ingrediente2);
    }

    // ==================== toEntity Tests ====================

    @Test
    void toEntity_QuandoDTOValido_DeveMapearParaEntity() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Pudim");
        dto.setDescricao("Pudim de leite condensado");
        dto.setTempoDePreparo(60);
        dto.setCategoriaId(1L);

        ReceitaIngredienteDTO ingDto1 = new ReceitaIngredienteDTO();
        ingDto1.setIngredienteId(1L);
        ingDto1.setQuantidade("2");
        ingDto1.setUnidade("xícaras");

        ReceitaIngredienteDTO ingDto2 = new ReceitaIngredienteDTO();
        ingDto2.setIngredienteId(2L);
        ingDto2.setQuantidade("1");
        ingDto2.setUnidade("lata");

        dto.setIngredientes(List.of(ingDto1, ingDto2));

        // Act
        Receita resultado = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pudim", resultado.getNome());
        assertEquals("Pudim de leite condensado", resultado.getDescricao());
        assertEquals(60, resultado.getTempoDePreparo());
        assertEquals(categoria, resultado.getCategoria());
        assertNotNull(resultado.getReceitaIngredientes());
        assertEquals(2, resultado.getReceitaIngredientes().size());

        // Verify ingredients mapping
        ReceitaIngrediente ri1 = resultado.getReceitaIngredientes().stream().findFirst().orElseThrow();
        assertEquals(ingrediente1.getId(), ri1.getIngrediente().getId());
        assertEquals("2", ri1.getQuantidade());
        assertEquals("xícaras", ri1.getUnidadeMedida());

        ReceitaIngrediente ri2 = resultado.getReceitaIngredientes().get(1);
        assertEquals(ingrediente2.getId(), ri2.getIngrediente().getId());
        assertEquals("1", ri2.getQuantidade());
        assertEquals("lata", ri2.getUnidadeMedida());
    }

    @Test
    void toEntity_QuandoIngredientesNulo_DeveMapearSemIngredientes() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Receita Simples");
        dto.setDescricao("Sem ingredientes");
        dto.setTempoDePreparo(30);
        dto.setCategoriaId(1L);
        dto.setIngredientes(null);

        // Act
        Receita resultado = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Assert
        assertNotNull(resultado);
        assertEquals("Receita Simples", resultado.getNome());
        assertNull(resultado.getReceitaIngredientes());
    }

    @Test
    void toEntity_QuandoIngredientesVazio_DeveMapearSemIngredientes() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Receita Vazia");
        dto.setDescricao("Sem ingredientes");
        dto.setTempoDePreparo(30);
        dto.setCategoriaId(1L);
        dto.setIngredientes(Collections.emptyList());

        // Act
        Receita resultado = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Assert
        assertNotNull(resultado);
        assertEquals("Receita Vazia", resultado.getNome());
        assertNotNull(resultado.getReceitaIngredientes());
        assertTrue(resultado.getReceitaIngredientes().isEmpty());
    }

    @Test
    void toEntity_QuandoIngredienteNaoEncontrado_DeveLancarIllegalArgumentException() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Receita Inválida");
        dto.setDescricao("Com ingrediente inválido");
        dto.setTempoDePreparo(30);
        dto.setCategoriaId(1L);

        ReceitaIngredienteDTO ingDto = new ReceitaIngredienteDTO();
        ingDto.setIngredienteId(999L); // ID que não existe
        ingDto.setQuantidade("1");
        ingDto.setUnidade("xícara");

        dto.setIngredientes(Collections.singletonList(ingDto));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis)
        );

        assertTrue(exception.getMessage().contains("Ingrediente não encontrado"));
        assertTrue(exception.getMessage().contains("999"));
    }

    @Test
    void toEntity_QuandoDescricaoNula_DeveMapearComDescricaoNula() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Receita");
        dto.setDescricao(null);
        dto.setTempoDePreparo(30);
        dto.setCategoriaId(1L);
        dto.setIngredientes(Collections.emptyList());

        // Act
        Receita resultado = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getDescricao());
    }

    @Test
    void toEntity_QuandoTempoZero_DeveMapearComTempoZero() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Receita");
        dto.setDescricao("Descrição");
        dto.setTempoDePreparo(0);
        dto.setCategoriaId(1L);
        dto.setIngredientes(Collections.emptyList());

        // Act
        Receita resultado = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.getTempoDePreparo());
    }

    @Test
    void toEntity_DeveDefinirReceitaNosIngredientes() {
        // Arrange
        ReceitaRequestDTO dto = new ReceitaRequestDTO();
        dto.setNome("Pudim");
        dto.setDescricao("Pudim");
        dto.setTempoDePreparo(60);
        dto.setCategoriaId(1L);

        ReceitaIngredienteDTO ingDto = new ReceitaIngredienteDTO();
        ingDto.setIngredienteId(1L);
        ingDto.setQuantidade("1");
        ingDto.setUnidade("xícara");

        dto.setIngredientes(Collections.singletonList(ingDto));

        // Act
        Receita resultado = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Assert
        assertNotNull(resultado);
        ReceitaIngrediente ri = resultado.getReceitaIngredientes().stream().findFirst().orElseThrow();
        assertNotNull(ri.getReceita());
        assertEquals(resultado, ri.getReceita());
    }

    // ==================== toDTO Tests ====================

    @Test
    void toDTO_QuandoReceitaValida_DeveMapearParaDTO() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Pudim");
        receita.setDescricao("Pudim de leite condensado");
        receita.setTempoDePreparo(60);
        receita.setCategoria(categoria);

        ReceitaIngrediente ri1 = new ReceitaIngrediente();
        ri1.setIngrediente(ingrediente1);
        ri1.setQuantidade("2");
        ri1.setUnidadeMedida("xícaras");

        ReceitaIngrediente ri2 = new ReceitaIngrediente();
        ri2.setIngrediente(ingrediente2);
        ri2.setQuantidade("1");
        ri2.setUnidadeMedida("lata");

        receita.setReceitaIngredientes(List.of(ri1, ri2));

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pudim", resultado.getNome());
        assertEquals("Pudim de leite condensado", resultado.getDescricao());
        assertEquals(60, resultado.getTempoDePreparo());
        assertEquals("Sobremesas", resultado.getCategoria());

        assertNotNull(resultado.getIngredientes());
        assertEquals(2, resultado.getIngredientes().size());

        ReceitaIngredienteDTO ingDto1 = resultado.getIngredientes().stream().findFirst().orElseThrow();
        assertEquals(1L, ingDto1.getIngredienteId());
        assertEquals("Açúcar", ingDto1.getNome());
        assertEquals("2", ingDto1.getQuantidade());
        assertEquals("xícaras", ingDto1.getUnidade());

        ReceitaIngredienteDTO ingDto2 = resultado.getIngredientes().get(1);
        assertEquals(2L, ingDto2.getIngredienteId());
        assertEquals("Leite Condensado", ingDto2.getNome());
        assertEquals("1", ingDto2.getQuantidade());
        assertEquals("lata", ingDto2.getUnidade());
    }

    @Test
    void toDTO_QuandoCategoriaNula_DeveMapearComCategoriaNula() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita");
        receita.setDescricao("Descrição");
        receita.setTempoDePreparo(30);
        receita.setCategoria(null);
        receita.setReceitaIngredientes(new ArrayList<>());

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getCategoria());
    }

    @Test
    void toDTO_QuandoIngredientesNulo_DeveRetornarListaVazia() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita");
        receita.setDescricao("Descrição");
        receita.setTempoDePreparo(30);
        receita.setCategoria(categoria);
        receita.setReceitaIngredientes(null);

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getIngredientes());
        assertTrue(resultado.getIngredientes().isEmpty());
    }

    @Test
    void toDTO_QuandoIngredientesVazio_DeveRetornarListaVazia() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita");
        receita.setDescricao("Descrição");
        receita.setTempoDePreparo(30);
        receita.setCategoria(categoria);
        receita.setReceitaIngredientes(new ArrayList<>());

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getIngredientes());
        assertTrue(resultado.getIngredientes().isEmpty());
    }

    @Test
    void toDTO_QuandoIngredienteComNuloIngrediente_DeveFiltraEIgnorar() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita");
        receita.setDescricao("Descrição");
        receita.setTempoDePreparo(30);
        receita.setCategoria(categoria);

        ReceitaIngrediente ri1 = new ReceitaIngrediente();
        ri1.setIngrediente(ingrediente1);
        ri1.setQuantidade("1");
        ri1.setUnidadeMedida("xícara");

        ReceitaIngrediente ri2 = new ReceitaIngrediente();
        ri2.setIngrediente(null); // Ingrediente nulo

        ReceitaIngrediente ri3 = new ReceitaIngrediente();
        ri3.setIngrediente(ingrediente2);
        ri3.setQuantidade("1");
        ri3.setUnidadeMedida("lata");

        receita.setReceitaIngredientes(List.of(ri1, ri2, ri3));

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getIngredientes().size());
        assertEquals("Açúcar", resultado.getIngredientes().stream().findFirst().orElseThrow().getNome());
        assertEquals("Leite Condensado", resultado.getIngredientes().get(1).getNome());
    }

    @Test
    void toDTO_QuandoReceitaIngredienteNulo_DeveFiltraEIgnorar() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita");
        receita.setDescricao("Descrição");
        receita.setTempoDePreparo(30);
        receita.setCategoria(categoria);

        ReceitaIngrediente ri1 = new ReceitaIngrediente();
        ri1.setIngrediente(ingrediente1);
        ri1.setQuantidade("1");
        ri1.setUnidadeMedida("xícara");

        List<ReceitaIngrediente> ingredientes = new ArrayList<>();
        ingredientes.add(ri1);
        ingredientes.add(null); // ReceitaIngrediente nulo

        receita.setReceitaIngredientes(ingredientes);

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIngredientes().size());
        assertEquals("Açúcar", resultado.getIngredientes().stream().findFirst().orElseThrow().getNome());
    }

    @Test
    void toDTO_QuandoDescricaoNula_DeveMapearComDescricaoNula() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita");
        receita.setDescricao(null);
        receita.setTempoDePreparo(30);
        receita.setCategoria(categoria);
        receita.setReceitaIngredientes(new ArrayList<>());

        // Act
        ReceitaResponseDTO resultado = ReceitaMapper.toDTO(receita);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getDescricao());
    }

    // ==================== toDTOList Tests ====================

    @Test
    void toDTOList_QuandoListaComMultiplasReceitas_DeveMapearTodas() {
        // Arrange
        Receita receita1 = new Receita();
        receita1.setId(1L);
        receita1.setNome("Pudim");
        receita1.setDescricao("Pudim de leite");
        receita1.setTempoDePreparo(60);
        receita1.setCategoria(categoria);
        receita1.setReceitaIngredientes(new ArrayList<>());

        Receita receita2 = new Receita();
        receita2.setId(2L);
        receita2.setNome("Bolo");
        receita2.setDescricao("Bolo de chocolate");
        receita2.setTempoDePreparo(45);
        receita2.setCategoria(categoria);
        receita2.setReceitaIngredientes(new ArrayList<>());

        List<Receita> receitas = List.of(receita1, receita2);

        // Act
        List<ReceitaResponseDTO> resultado = ReceitaMapper.toDTOList(receitas);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals("Pudim", resultado.stream().findFirst().orElseThrow().getNome());
        assertEquals(60, resultado.stream().findFirst().orElseThrow().getTempoDePreparo());

        assertEquals("Bolo", resultado.get(1).getNome());
        assertEquals(45, resultado.get(1).getTempoDePreparo());
    }

    @Test
    void toDTOList_QuandoListaVazia_DeveRetornarListaVazia() {
        // Arrange
        List<Receita> receitas = Collections.emptyList();

        // Act
        List<ReceitaResponseDTO> resultado = ReceitaMapper.toDTOList(receitas);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void toDTOList_QuandoListaComUmaReceita_DeveMapearCorretamente() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Pudim");
        receita.setDescricao("Pudim");
        receita.setTempoDePreparo(60);
        receita.setCategoria(categoria);
        receita.setReceitaIngredientes(new ArrayList<>());

        List<Receita> receitas = Collections.singletonList(receita);

        // Act
        List<ReceitaResponseDTO> resultado = ReceitaMapper.toDTOList(receitas);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pudim", resultado.stream().findFirst().orElseThrow().getNome());
    }

    // ==================== Round-trip Tests ====================

    @Test
    void roundTrip_DeveMapearEntityParaDTOEVoltaComSucesso() {
        // Arrange: Create a DTO and map to entity
        ReceitaRequestDTO requestDTO = new ReceitaRequestDTO();
        requestDTO.setNome("Pudim");
        requestDTO.setDescricao("Pudim de leite condensado");
        requestDTO.setTempoDePreparo(60);
        requestDTO.setCategoriaId(1L);

        ReceitaIngredienteDTO ingDto = new ReceitaIngredienteDTO();
        ingDto.setIngredienteId(1L);
        ingDto.setQuantidade("2");
        ingDto.setUnidade("xícaras");
        requestDTO.setIngredientes(Collections.singletonList(ingDto));

        // Act & Assert: Map to entity
        Receita receita = ReceitaMapper.toEntity(requestDTO, categoria, ingredientesDisponiveis);
        assertNotNull(receita);
        assertEquals("Pudim", receita.getNome());

        // Map back to DTO
        ReceitaResponseDTO responseDTO = ReceitaMapper.toDTO(receita);
        assertNotNull(responseDTO);
        assertEquals("Pudim", responseDTO.getNome());
        assertEquals("Pudim de leite condensado", responseDTO.getDescricao());
        assertEquals(60, responseDTO.getTempoDePreparo());
        assertEquals("Sobremesas", responseDTO.getCategoria());
        assertEquals(1, responseDTO.getIngredientes().size());
        assertEquals("Açúcar", responseDTO.getIngredientes().stream().findFirst().orElseThrow().getNome());
    }
}

