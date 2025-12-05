package org.grupo1.gestordereceitas.repository;

import org.grupo1.gestordereceitas.model.Ingrediente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IngredienteRepositoryIntegrationTest {
    private Ingrediente ingrediente;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ReceitaRepository receitaRepository; // Se disponível


    @BeforeEach
    void setUp() {
        // Ensure a clean database state
        receitaRepository.deleteAll();
        ingredienteRepository.deleteAll();

        // Criar ingrediente de teste
        ingrediente = new Ingrediente();
        ingrediente.setNome("Farinha de Trigo");
    }

    @AfterEach
    void tearDown() {
        receitaRepository.deleteAll();
        // Limpar o banco de dados após cada teste
        ingredienteRepository.deleteAll();
    }

    @Test
    public void salvarIngrediente_DeveMapearNoH2() {
        // Act
        Ingrediente ingredienteSalvo = ingredienteRepository.save(ingrediente);

        // Assert
        assertNotNull(ingredienteSalvo.getId());
        assertEquals("Farinha de Trigo", ingredienteSalvo.getNome());
    }

    @Test
    public void buscarIngredientePorId_DeveRetornarIngrediente() {
        // Arrange
        Ingrediente ingredienteSalvo = ingredienteRepository.save(ingrediente);

        // Act
        Optional<Ingrediente> ingredienteEncontrado = ingredienteRepository.findById(ingredienteSalvo.getId());

        // Assert
        assertTrue(ingredienteEncontrado.isPresent());
        assertEquals("Farinha de Trigo", ingredienteEncontrado.get().getNome());
    }

    @Test
    public void buscarTodos_DeveRetornarListaComIngredientes() {
        // Arrange
        ingredienteRepository.save(ingrediente);

        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setNome("Açúcar");

        ingredienteRepository.save(ingrediente2);

        // Act
        var ingredientes = ingredienteRepository.findAll();

        // Assert
        assertEquals(2, ingredientes.size());
    }

    @Test
    public void atualizarIngrediente_DeveAtualizarNoH2() {
        // Arrange
        Ingrediente ingredienteSalvo = ingredienteRepository.save(ingrediente);

        // Act
        ingredienteSalvo.setNome("Farinha Integral");
        Ingrediente ingredienteAtualizado = ingredienteRepository.save(ingredienteSalvo);

        // Assert
        assertEquals("Farinha Integral", ingredienteAtualizado.getNome());
    }

    @Test
    public void deletarIngrediente_DeveRemoverDoH2() {
        // Arrange
        Ingrediente ingredienteSalvo = ingredienteRepository.save(ingrediente);
        Long ingredienteId = ingredienteSalvo.getId();

        // Act
        ingredienteRepository.deleteById(ingredienteId);

        // Assert
        Optional<Ingrediente> ingredienteEncontrado = ingredienteRepository.findById(ingredienteId);
        assertTrue(ingredienteEncontrado.isEmpty());
    }
}

