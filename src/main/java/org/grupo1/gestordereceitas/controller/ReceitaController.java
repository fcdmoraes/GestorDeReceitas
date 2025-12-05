package org.grupo1.gestordereceitas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.service.ReceitaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
@Tag(name = "Receitas", description = "Operações de CRUD para o gerenciamento de receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @Operation(
            summary = "Lista todas as receitas",
            description = "Retorna uma lista contendo todas as receitas cadastradas no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public List<ReceitaResponseDTO> listarTodas() {
        return receitaService.listarTodas();
    }

    @Operation(
            summary = "Busca uma receita pelo ID",
            description = "Retorna os detalhes de uma receita específica, caso ela exista."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @GetMapping("/{id}")
    public ReceitaResponseDTO buscarPorId(@PathVariable Long id) {
        return receitaService.buscarPorId(id);
    }

    @Operation(
            summary = "Cria uma nova receita",
            description = "Cadastra uma nova receita com as informações fornecidas no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receita criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da receita")
    })
    @PostMapping
    public ReceitaResponseDTO criarReceita(@Validated(ReceitaRequestDTO.OnCreate.class) @RequestBody ReceitaRequestDTO dto) {
        return receitaService.salvar(dto);
    }

    @Operation(
            summary = "Atualiza uma receita existente",
            description = "Atualiza completamente uma receita com base no ID fornecido e nos novos dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @PutMapping("/{id}")
    public ReceitaResponseDTO atualizarReceita(@PathVariable Long id, @Validated(ReceitaRequestDTO.OnUpdate.class) @RequestBody ReceitaRequestDTO dto) {
        return receitaService.atualizar(id, dto);
    }

    @Operation(
            summary = "Atualiza parcialmente uma receita",
            description = "Permite modificar apenas alguns campos de uma receita existente, sem substituir todos os dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita atualizada parcialmente com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @PatchMapping("/{id}")
    public ReceitaResponseDTO atualizarParcialReceita(@PathVariable Long id, @Validated(ReceitaRequestDTO.OnPartialUpdate.class) @RequestBody ReceitaRequestDTO dto) {
        return receitaService.atualizarParcial(id, dto);
    }

    @Operation(
            summary = "Deleta uma receita",
            description = "Remove permanentemente uma receita com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receita deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    @DeleteMapping("/{id}")
    public void deletarReceita(@PathVariable Long id) {
        receitaService.deletar(id);
    }
}


