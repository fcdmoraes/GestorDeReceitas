package org.grupo1.gestordereceitas.dto.mapper;

import org.grupo1.gestordereceitas.dto.CategoriaDTO;
import org.grupo1.gestordereceitas.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }
}
