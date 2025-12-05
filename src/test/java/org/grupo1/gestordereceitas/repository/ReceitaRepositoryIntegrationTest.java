package org.grupo1.gestordereceitas.repository;

import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Receita;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReceitaRepositoryIntegrationTest {
    private Categoria categoria;
    private Receita receita;
    private Receita receitaSalva;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    private void salvaReceita() {
        receitaSalva = receitaRepository.save(receita);
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    void setUp() {
        // Ensure a clean database state in case data.sql or other tests populated data
        receitaRepository.deleteAll();
        categoriaRepository.deleteAll();

        // Criar categorias e receitas de teste
        categoria = new Categoria();
        categoria.setNome("Sobremesa");
        entityManager.persistAndFlush(categoria);

        receita = new Receita();
        receita.setNome("Bolo de Chocolate");
        receita.setDescricao("Delicioso bolo de chocolate");
        receita.setTempoDePreparo(60);
        receita.setCategoria(categoria);
    }

    @AfterEach
    void tearDown() {
        // Limpar o banco de dados após cada teste
        receitaRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

    @Test
    public void salvarReceita_DeveMapearNoH2() {
        // Act
        salvaReceita();

        // Assert
        assertNotNull(receitaSalva.getId());
        assertEquals("Bolo de Chocolate", receitaSalva.getNome());
        assertEquals("Sobremesa", receitaSalva.getCategoria().getNome());
        assertEquals(60, receitaSalva.getTempoDePreparo());
        assertEquals(categoria.getId(), receitaSalva.getCategoria().getId());
    }

    @Test
    public void atualizarReceita_DeveAtualizarNoH2() {
        // Arrange
        salvaReceita();

        // Act
        receitaSalva.setNome("Bolo de Baunilha");
        receitaSalva.setTempoDePreparo(90);
        Receita receitaAtualizada = receitaRepository.save(receitaSalva);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals("Bolo de Baunilha", receitaAtualizada.getNome());
        assertEquals(90, receitaAtualizada.getTempoDePreparo());
    }

    @Test
    public void buscarTodas_DeveRetornarListaComReceitas() {
        // Arrange
        Receita receita2 = new Receita();
        receita2.setNome("Pudim");
        receita2.setDescricao("Pudim de Abóbora com especiarias");
        receita2.setTempoDePreparo(45);
        receita2.setCategoria(categoria);

        receitaRepository.save(receita2);
        salvaReceita();

        // Act
        var receitas = receitaRepository.findAll();

        // Assert
        assertEquals(2, receitas.size());
    }

    @Test
    public void buscarReceiraPorId_DeveRetornarReceita() {
        // Arrange
        salvaReceita();

        // Act
        var receitaEncontrada = receitaRepository.findById(receitaSalva.getId());

        // Assert
        assertEquals(receitaSalva.getNome(), receitaEncontrada.get().getNome());
    }

    @Test
    public void deletarReceita_DeveRemoverDoH2() {
        // Arrange
        salvaReceita();
        Long receitaId = receitaSalva.getId();

        // Act
        receitaRepository.deleteById(receitaId);
        entityManager.flush();

        // Assert
        Optional<Receita> receitaEncontrada = receitaRepository.findById(receitaId);
        assertTrue(receitaEncontrada.isEmpty());
    }




}
