package org.grupo1.gestordereceitas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceitaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private int tempoDePreparo;
    private String categoria;
    private List<ReceitaIngredienteDTO> ingredientes;
}
