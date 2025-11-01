package org.grupo1.gestordereceitas;

import org.grupo1.gestordereceitas.model.*;
import org.grupo1.gestordereceitas.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/* CLASSE TEMPORARIA PARA TESTE DOS REPOSITORIOS */
@Component
public class TestRepository implements CommandLineRunner {

    private final ReceitaRepository receitaRepository;
    private final CategoriaRepository categoriaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ReceitaIngredienteRepository receitaIngredienteRepository;

    public TestRepository(ReceitaRepository receitaRepository,
                           CategoriaRepository categoriaRepository,
                           IngredienteRepository ingredienteRepository,
                           ReceitaIngredienteRepository receitaIngredienteRepository) {
        this.receitaRepository = receitaRepository;
        this.categoriaRepository = categoriaRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.receitaIngredienteRepository = receitaIngredienteRepository;
    }

    @Transactional
    @Override
    public void run(String... args) {
        System.out.println("\n==============================");
        System.out.println("✅ TESTANDO REPOSITÓRIOS");
        System.out.println("==============================");

        // Quantidade de registros por tabela
        System.out.println("Categorias: " + categoriaRepository.count());
        System.out.println("Ingredientes: " + ingredienteRepository.count());
        System.out.println("Receitas: " + receitaRepository.count());
        System.out.println("ReceitaIngrediente: " + receitaIngredienteRepository.count());

        System.out.println("\n--- Categorias ---");
        categoriaRepository.findAll().forEach(c ->
                System.out.println("• " + c.getId() + " - " + c.getNome())
        );

        System.out.println("\n--- Ingredientes ---");
        ingredienteRepository.findAll().forEach(i ->
                System.out.println("• " + i.getId() + " - " + i.getNome())
        );

        System.out.println("\n--- Receitas ---");
        receitaRepository.findAll().forEach(r -> {
            System.out.println("🍽 " + r.getId() + " - " + r.getNome());
            System.out.println("   Categoria: " + (r.getCategoria() != null ? r.getCategoria().getNome() : "Sem categoria"));
            System.out.println("   Ingredientes:");
            r.getReceitaIngredientes().forEach(ri ->
                    System.out.println("     - " + ri.getIngrediente().getNome() + " (" + ri.getQuantidade() + " " + ri.getUnidadeMedida() + ")")
            );
            System.out.println();
        });

        System.out.println("==============================");
        System.out.println("🎉 TESTE FINALIZADO COM SUCESSO!");
        System.out.println("==============================\n");
    }
}
