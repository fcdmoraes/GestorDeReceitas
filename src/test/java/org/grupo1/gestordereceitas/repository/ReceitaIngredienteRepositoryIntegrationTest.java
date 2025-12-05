package org.grupo1.gestordereceitas.repository;

import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.model.ReceitaIngrediente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReceitaIngredienteRepositoryIntegrationTest {
    private Receita receita;
    private Ingrediente ingrediente;
    private ReceitaIngrediente receitaIngrediente;
    private ReceitaIngrediente receitaIngredienteSalva;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReceitaIngredienteRepository receitaIngredienteRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private void salvaReceitaIngrediente() {
        receitaIngredienteSalva = receitaIngredienteRepository.save(receitaIngrediente);
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    void setUp() {
        // Ensure a clean database state
        receitaIngredienteRepository.deleteAll();
        receitaRepository.deleteAll();
        ingredienteRepository.deleteAll();
        categoriaRepository.deleteAll();

        // Criar categoria
        Categoria categoria = new Categoria();
        categoria.setNome("Sobremesas");
        entityManager.persistAndFlush(categoria);

        // Criar receita
        receita = new Receita();
        receita.setNome("Bolo de Chocolate");
        receita.setDescricao("Delicioso bolo");
        receita.setTempoDePreparo(60);
        receita.setCategoria(categoria);
        entityManager.persistAndFlush(receita);

        // Criar ingrediente
        ingrediente = new Ingrediente();
        ingrediente.setNome("Farinha de Trigo");
        entityManager.persistAndFlush(ingrediente);

        // Criar relação receita-ingrediente
        receitaIngrediente = new ReceitaIngrediente();
        receitaIngrediente.setReceita(receita);
        receitaIngrediente.setIngrediente(ingrediente);
        receitaIngrediente.setQuantidade("duas");
        receitaIngrediente.setUnidadeMedida("xícaras");
    }

    @AfterEach
    void tearDown() {
        // Limpar o banco de dados após cada teste
        receitaIngredienteRepository.deleteAll();
        receitaRepository.deleteAll();
        ingredienteRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

    @Test
    public void salvarReceitaIngrediente_DeveMapearNoH2() {
        // Act
        salvaReceitaIngrediente();

        // Assert
        assertNotNull(receitaIngredienteSalva.getId());
        assertEquals("duas", receitaIngredienteSalva.getQuantidade());
        assertEquals("xícaras", receitaIngredienteSalva.getUnidadeMedida());
        assertEquals("Bolo de Chocolate", receitaIngredienteSalva.getReceita().getNome());
        assertEquals("Farinha de Trigo", receitaIngredienteSalva.getIngrediente().getNome());
    }

    @Test
    public void buscarReceitaIngredientePorId_DeveRetornarReceitaIngrediente() {
        // Arrange
        salvaReceitaIngrediente();

        // Act
        Optional<ReceitaIngrediente> receitaIngredienteEncontrada =
            receitaIngredienteRepository.findById(receitaIngredienteSalva.getId());

        // Assert
        assertTrue(receitaIngredienteEncontrada.isPresent());
        assertEquals("duas", receitaIngredienteEncontrada.get().getQuantidade());
        assertEquals("xícaras", receitaIngredienteEncontrada.get().getUnidadeMedida());
    }

    @Test
    public void buscarTodos_DeveRetornarListaComReceitasIngredientes() {
        // Arrange
        ReceitaIngrediente receitaIngrediente2 = new ReceitaIngrediente();
        receitaIngrediente2.setReceita(receita);
        receitaIngrediente2.setIngrediente(ingrediente);
        receitaIngrediente2.setQuantidade("três");
        receitaIngrediente2.setUnidadeMedida("colheres");

        receitaIngredienteRepository.save(receitaIngrediente2);
        salvaReceitaIngrediente();

        // Act
        var receitasIngredientes = receitaIngredienteRepository.findAll();

        // Assert
        assertEquals(2, receitasIngredientes.size());
    }

    @Test
    public void atualizarReceitaIngrediente_DeveAtualizarNoH2() {
        // Arrange
        salvaReceitaIngrediente();

        // Act
        receitaIngredienteSalva.setQuantidade("1/2");
        receitaIngredienteSalva.setUnidadeMedida("colher");
        ReceitaIngrediente receitaIngredienteAtualizada =
            receitaIngredienteRepository.save(receitaIngredienteSalva);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals("1/2", receitaIngredienteAtualizada.getQuantidade());
        assertEquals("colher", receitaIngredienteAtualizada.getUnidadeMedida());
    }

    @Test
    public void deletarReceitaIngrediente_DeveRemoverDoH2() {
        // Arrange
        salvaReceitaIngrediente();
        Long receitaIngredienteId = receitaIngredienteSalva.getId();

        // Act
        receitaIngredienteRepository.deleteById(receitaIngredienteId);
        entityManager.flush();

        // Assert
        Optional<ReceitaIngrediente> receitaIngredienteEncontrada =
            receitaIngredienteRepository.findById(receitaIngredienteId);
        assertTrue(receitaIngredienteEncontrada.isEmpty());
    }

    @Test
    public void receitaIngrediente_DeveManterRelacionamentosComReceitaEIngrediente() {
        // Arrange
        salvaReceitaIngrediente();

        // Act
        Optional<ReceitaIngrediente> receitaIngredienteEncontrada =
            receitaIngredienteRepository.findById(receitaIngredienteSalva.getId());

        // Assert
        assertTrue(receitaIngredienteEncontrada.isPresent());
        assertNotNull(receitaIngredienteEncontrada.get().getReceita());
        assertNotNull(receitaIngredienteEncontrada.get().getIngrediente());
        assertEquals("Bolo de Chocolate", receitaIngredienteEncontrada.get().getReceita().getNome());
        assertEquals("Farinha de Trigo", receitaIngredienteEncontrada.get().getIngrediente().getNome());
    }
}

