package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.dto.ReceitaIngredienteDTO;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.model.ReceitaIngrediente;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ReceitaMapper {

    public static Receita toEntity(
            ReceitaRequestDTO dto,
            Categoria categoria,
            List<Ingrediente> ingredientesDisponiveis
    ) {
        Receita receita = new Receita();
        receita.setNome(dto.getNome());
        receita.setDescricao(dto.getDescricao());
        receita.setTempoDePreparo(dto.getTempoDePreparo());
        receita.setCategoria(categoria);

        if (dto.getIngredientes() != null) {
            List<ReceitaIngrediente> receitaIngredientes = dto.getIngredientes().stream()
                    .map(i -> {
                        ReceitaIngrediente ri = new ReceitaIngrediente();
                        ri.setReceita(receita);

                        // Busca o ingrediente pelo ID enviado no DTO
                        Ingrediente ingrediente = ingredientesDisponiveis.stream()
                                .filter(ing -> ing.getId().equals(i.getIngredienteId()))
                                .findFirst()
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Ingrediente não encontrado: ID " + i.getIngredienteId()));

                        ri.setIngrediente(ingrediente);
                        ri.setQuantidade(i.getQuantidade());
                        ri.setUnidadeMedida(i.getUnidade());
                        return ri;
                    })
                    .toList();

            receita.setReceitaIngredientes(receitaIngredientes);
        }

        return receita;
    }

    public static ReceitaResponseDTO toDTO(Receita receita) {
        ReceitaResponseDTO dto = new ReceitaResponseDTO();
        dto.setId(receita.getId());
        dto.setNome(receita.getNome());
        dto.setDescricao(receita.getDescricao());
        dto.setTempoDePreparo(receita.getTempoDePreparo());
        dto.setCategoria(receita.getCategoria() != null ? receita.getCategoria().getNome() : null);

        if (receita.getReceitaIngredientes() != null) {
            List<ReceitaIngredienteDTO> ingredientes = receita.getReceitaIngredientes().stream()
                    .filter(ri -> ri != null && ri.getIngrediente() != null)
                    .map(ri -> {
                        ReceitaIngredienteDTO ingDTO = new ReceitaIngredienteDTO();
                        ingDTO.setIngredienteId(ri.getIngrediente().getId());
                        ingDTO.setNome(ri.getIngrediente().getNome());
                        ingDTO.setQuantidade(ri.getQuantidade());
                        ingDTO.setUnidade(ri.getUnidadeMedida());
                        return ingDTO;
                    })
                    .toList();

            dto.setIngredientes(ingredientes);
        } else {
            dto.setIngredientes(Collections.emptyList());
        }

        return dto;
    }

    public static List<ReceitaResponseDTO> toDTOList(List<Receita> receitas) {
        return receitas.stream()
                .map(ReceitaMapper::toDTO)
                .toList();
    }
}
