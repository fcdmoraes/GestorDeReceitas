package org.grupo1.gestordereceitas.service;

import org.grupo1.gestordereceitas.exception.ResourceNotFoundException;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.repository.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public List<Ingrediente> listarTodos() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente buscarPorId(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente com ID " + id + " não encontrado"));
    }

    public Ingrediente salvar(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente atualizar(Long id, Ingrediente ingrediente) {
        Ingrediente existente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente com ID " + id + " não encontrado"));
        existente.setNome(ingrediente.getNome());
        return ingredienteRepository.save(existente);
    }

    public void deletar(Long id) {
        if (!ingredienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente com ID " + id + " não encontrado para exclusão");
        }
        ingredienteRepository.deleteById(id);
    }
}
