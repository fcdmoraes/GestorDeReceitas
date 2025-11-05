package org.grupo1.gestordereceitas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceitaRequestDTO {
    private String nome;
    private String descricao;
    private int tempoDePreparo;
    private Long categoriaId;
    private List<ReceitaIngredienteDTO> ingredientes;
}