package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.ReceitaIngredienteDTO;
import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.model.ReceitaIngrediente;

import java.util.List;
import java.util.stream.Collectors;

public class ReceitaMapper {

    public static Receita toEntity(ReceitaRequestDTO dto, Categoria categoria, List<Ingrediente> ingredientesDisponiveis) {
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
    }

    public static ReceitaResponseDTO toDTO(Receita receita) {
        ReceitaResponseDTO dto = new ReceitaResponseDTO();
        dto.setId(receita.getId());
        dto.setNome(receita.getNome());
        dto.setDescricao(receita.getDescricao());
        dto.setTempoDePreparo(receita.getTempoDePreparo());
        dto.setCategoria(receita.getCategoria() != null ? receita.getCategoria().getNome() : null);

        if (receita.getReceitaIngredientes() != null) {
            dto.setIngredientes(receita.getReceitaIngredientes().stream()
                    .map(ri -> {
                        ReceitaIngredienteDTO dtoRI = new ReceitaIngredienteDTO();
                        dtoRI.setIngredienteNome(ri.getIngrediente().getNome());
                        dtoRI.setQuantidade(ri.getQuantidade());
                        return dtoRI;
                    })
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static List<ReceitaResponseDTO> toDTOList(List<Receita> receitas) {
        return receitas.stream()
                .map(ReceitaMapper::toDTO)
                .collect(Collectors.toList());
    }
}