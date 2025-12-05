package org.grupo1.gestordereceitas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceitaRequestDTO {

    // Marker interfaces for validation groups
    public interface OnCreate extends Default {}
    public interface OnUpdate extends Default {}
    public interface OnPartialUpdate {}

    @NotBlank(message = "Nome é obrigatório", groups = {OnCreate.class, OnUpdate.class})
    private String nome;

    private String descricao;

    @Min(value = 0, message = "Tempo de preparo não pode ser negativo")
    private int tempoDePreparo;

    @NotNull(message = "Categoria é obrigatória", groups = {OnCreate.class, OnUpdate.class})
    private Long categoriaId;

    private List<ReceitaIngredienteDTO> ingredientes;
}