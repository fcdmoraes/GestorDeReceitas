package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.IngredienteDTO;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.springframework.stereotype.Component;

@Component
public class IngredienteMapper {

    public static IngredienteDTO toDTO(Ingrediente ingrediente) {
        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(ingrediente.getId());
        dto.setNome(ingrediente.getNome());
        return dto;
    }
}
