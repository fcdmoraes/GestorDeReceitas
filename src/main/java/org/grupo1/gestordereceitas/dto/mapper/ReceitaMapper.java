package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;

import java.util.List;
import java.util.stream.Collectors;

public class ReceitaMapper {

    public static Receita toEntity(ReceitaRequestDTO dto, Categoria categoria) {
        Receita receita = new Receita();
        receita.setNome(dto.getNome());
        receita.setDescricao(dto.getDescricao());
        receita.setTempoDePreparo(dto.getTempoDePreparo());
        receita.setCategoria(categoria);

        if (dto.getIngredientes() != null) {
            List<Ingrediente> ingredientes = dto.getIngredientes()
                    .stream()
                    .map(i -> {
                        Ingrediente ing = new Ingrediente();
                        ing.setNome(i.getNome());
                        ing.setQuantidade(i.getQuantidade());
                        ing.setReceita(receita);
                        return ing;
            }).collect(Collectors.toList());
            receita.setIngredientes(ingredientes);
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

        if (receita.getIngredientes() != null) {
            dto.setIngredientes(receita.getIngredientes().stream()
                    .map(Ingrediente::getNome)
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
