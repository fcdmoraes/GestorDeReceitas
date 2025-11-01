package org.grupo1.gestordereceitas.controller;

import org.grupo1.gestordereceitas.dto.IngredienteDTO;
import org.grupo1.gestordereceitas.dto.mapper.IngredienteMapper;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.repository.IngredienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteController(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    @GetMapping
    public List<IngredienteDTO> getIngredientes() {
        return ingredienteRepository.findAll()
                .stream()
                .map(IngredienteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public IngredienteDTO getIngredienteById(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingrediente não encontrado"
                ));
        return IngredienteMapper.toDTO(ingrediente);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredienteById(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingrediente não encontrado"
                ));
        ingredienteRepository.delete(ingrediente);
    }
}
