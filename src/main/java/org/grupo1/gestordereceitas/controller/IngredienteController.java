package org.grupo1.gestordereceitas.controller;

import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.service.IngredienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @GetMapping
    public List<Ingrediente> listarTodos() {
        return ingredienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Ingrediente buscarPorId(@PathVariable Long id) {
        return ingredienteService.buscarPorId(id);
    }

    @PostMapping
    public Ingrediente criarIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteService.salvar(ingrediente);
    }

    @PutMapping("/{id}")
    public Ingrediente atualizarIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        return ingredienteService.atualizar(id, ingrediente);
    }

    @DeleteMapping("/{id}")
    public void deletarIngrediente(@PathVariable Long id) {
        ingredienteService.deletar(id);
    }
}