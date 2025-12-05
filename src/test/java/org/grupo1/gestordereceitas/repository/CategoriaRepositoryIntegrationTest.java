package org.grupo1.gestordereceitas.repository;

import org.grupo1.gestordereceitas.model.Categoria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoriaRepositoryIntegrationTest {
    private Categoria categoria;
    private Categoria categoriaSalva;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private void salvaCategoria() {
        categoriaSalva = categoriaRepository.save(categoria);
    }

    @BeforeEach
    void setUp() {
        // Ensure a clean database state
        receitaRepository.deleteAll();
        categoriaRepository.deleteAll();

        // Criar categoria de teste
        categoria = new Categoria();
        categoria.setNome("Sobremesas");
    }

    @AfterEach
    void tearDown() {
        // Limpar o banco de dados após cada teste
        categoriaRepository.deleteAll();
    }

    @Test
    public void salvarCategoria_DeveMapearNoH2() {
        // Act
        salvaCategoria();

        // Assert
        assertNotNull(categoriaSalva.getId());
        assertEquals("Sobremesas", categoriaSalva.getNome());
    }

    @Test
    public void buscarCategoriaPorId_DeveRetornarCategoria() {
        // Arrange
        salvaCategoria();

        // Act
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(categoriaSalva.getId());

        // Assert
        assertTrue(categoriaEncontrada.isPresent());
        assertEquals("Sobremesas", categoriaEncontrada.get().getNome());
    }

    @Test
    public void buscarTodas_DeveRetornarListaComCategorias() {
        // Arrange
        Categoria categoria2 = new Categoria();
        categoria2.setNome("Lanches");

        categoriaRepository.save(categoria2);
        salvaCategoria();

        // Act
        var categorias = categoriaRepository.findAll();

        // Assert
        assertEquals(2, categorias.size());
    }

    @Test
    public void atualizarCategoria_DeveAtualizarNoH2() {
        // Arrange
        salvaCategoria();

        // Act
        categoriaSalva.setNome("Doces");
        Categoria categoriaAtualizada = categoriaRepository.save(categoriaSalva);

        // Assert
        assertEquals("Doces", categoriaAtualizada.getNome());
    }

    @Test
    public void deletarCategoria_DeveRemoverDoH2() {
        // Arrange
        salvaCategoria();
        Long categoriaId = categoriaSalva.getId();

        // Act
        categoriaRepository.deleteById(categoriaId);

        // Assert
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(categoriaId);
        assertTrue(categoriaEncontrada.isEmpty());
    }
}

