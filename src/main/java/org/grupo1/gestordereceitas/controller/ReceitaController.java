package org.grupo1.gestordereceitas.controller;

import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.service.ReceitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @GetMapping
    public List<ReceitaResponseDTO> listarTodas() {
        return receitaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ReceitaResponseDTO buscarPorId(@PathVariable Long id) {
        return receitaService.buscarPorId(id);
    }

    @PostMapping
    public ReceitaResponseDTO criarReceita(@RequestBody ReceitaRequestDTO dto) {
        return receitaService.salvar(dto);
    }

    @PutMapping("/{id}")
    public ReceitaResponseDTO atualizarReceita(@PathVariable Long id, @RequestBody ReceitaRequestDTO dto) {
        return receitaService.atualizar(id, dto);
    }

    @PatchMapping("/{id}")
    public ReceitaResponseDTO atualizarParcialReceita(@PathVariable Long id, @RequestBody ReceitaRequestDTO dto) {
        return receitaService.atualizarParcial(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletarReceita(@PathVariable Long id) {
        receitaService.deletar(id);
    }
}