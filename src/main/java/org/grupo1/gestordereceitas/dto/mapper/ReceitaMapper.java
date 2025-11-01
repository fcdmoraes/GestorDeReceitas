package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.ReceitaDTO;
import org.grupo1.gestordereceitas.dto.ReceitaIngredienteDTO;
import org.grupo1.gestordereceitas.model.Receita;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ReceitaMapper {

/*    public static Receita toEntity(ReceitaRequestDTO dto, Categoria categoria, List<Ingrediente> ingredientesDisponiveis) {
        Receita receita = new Receita();
        receita.setNome(dto.getNome());
        receita.setDescricao(dto.getDescricao());
        receita.setTempoDePreparo(dto.getTempoDePreparo());
        receita.setCategoria(categoria);

        if (dto.getIngredientes() != null) {
            List<ReceitaIngrediente> receitaIngredientes = dto.getIngredientes()
                    .stream()
                    .map(i -> {
                        ReceitaIngrediente ri = new ReceitaIngrediente();
                        ri.setReceita(receita);

                        // Busca o ingrediente na lista de disponíveis (ou de um repositório)
                        Ingrediente ingrediente = ingredientesDisponiveis.stream()
                                .filter(ing -> ing.getId().equals(i.getIngredienteId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Ingrediente não encontrado: " + i.getIngredienteId()));

                        ri.setIngrediente(ingrediente);
                        ri.setQuantidade(i.getQuantidade());
                        return ri;
                    })
                    .collect(Collectors.toList());

            receita.setReceitaIngredientes(receitaIngredientes);
        }
        return receita;
    }*/

    public static ReceitaDTO toDTO(Receita receita) {
        ReceitaDTO dto = new ReceitaDTO();
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
                        ingDTO.setNome(ri.getIngrediente().getNome());
                        ingDTO.setQuantidade(ri.getQuantidade() != null ? ri.getQuantidade() : "");
                        ingDTO.setUnidade(ri.getUnidadeMedida() != null ? ri.getUnidadeMedida() : "");
                        return ingDTO;
                    })
                    .toList();

            dto.setIngredientes(ingredientes);
        } else {
            dto.setIngredientes(Collections.emptyList());
        }

        return dto;
    }
}

/*    public static List<ReceitaDTO> toDTOList(List<Receita> receitas) {
        return receitas.stream()
                .map(ReceitaMapper::toDTO)
                .collect(Collectors.toList());
    }*/
