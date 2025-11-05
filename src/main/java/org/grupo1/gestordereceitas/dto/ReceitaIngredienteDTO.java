package org.grupo1.gestordereceitas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceitaIngredienteDTO {
    private Long ingredienteId;
    private String nome;
    private String quantidade;
    private String unidade;
}
