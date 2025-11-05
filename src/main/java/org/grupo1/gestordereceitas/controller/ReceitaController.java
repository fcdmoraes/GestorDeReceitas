package org.grupo1.gestordereceitas.controller;

import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.dto.mapper.ReceitaMapper;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.repository.ReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaRepository receitaRepository;

    public ReceitaController(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    @GetMapping
    public List<ReceitaResponseDTO> getReceitas() {
        return receitaRepository.findAll()
                .stream()
                .map(ReceitaMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ReceitaResponseDTO getReceitaById(@PathVariable Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Receita não encontrada"
                ));
        return ReceitaMapper.toDTO(receita);
    }

    @DeleteMapping("/{id}")
    public void deleteReceitaById(@PathVariable Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Receita não encontrada"
                ));
        receitaRepository.delete(receita);
    }
}
