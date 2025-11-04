package org.grupo1.gestordereceitas.controller;


import org.grupo1.gestordereceitas.dto.CategoriaDTO;
import org.grupo1.gestordereceitas.dto.mapper.CategoriaMapper;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<CategoriaDTO> getCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoriaDTO getCategoriaById(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoria não encontrada"
                ));
        return CategoriaMapper.toDTO(categoria);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoriaById(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoria não encontrada"
                ));
        categoriaRepository.delete(categoria);
    }
}
