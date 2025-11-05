package org.grupo1.gestordereceitas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.service.IngredienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@Tag(name = "Ingredientes", description = "Operações relacionadas ao gerenciamento de ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @Operation(summary = "Lista todos os ingredientes", description = "Retorna uma lista de todos os ingredientes cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public List<Ingrediente> listarTodos() {
        return ingredienteService.listarTodos();
    }

    @Operation(summary = "Busca um ingrediente pelo ID", description = "Retorna os detalhes de um ingrediente específico se ele existir.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @GetMapping("/{id}")
    public Ingrediente buscarPorId(@PathVariable Long id) {
        return ingredienteService.buscarPorId(id);
    }

    @Operation(summary = "Cria um novo ingrediente", description = "Cadastra um novo ingrediente no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingrediente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados enviados")
    })
    @PostMapping
    public Ingrediente criarIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteService.salvar(ingrediente);
    }

    @Operation(summary = "Atualiza um ingrediente existente", description = "Modifica todos os dados de um ingrediente com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @PutMapping("/{id}")
    public Ingrediente atualizarIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        return ingredienteService.atualizar(id, ingrediente);
    }

    @Operation(summary = "Deleta um ingrediente", description = "Remove um ingrediente do cadastro pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ingrediente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado")
    })
    @DeleteMapping("/{id}")
    public void deletarIngrediente(@PathVariable Long id) {
        ingredienteService.deletar(id);
    }
}
