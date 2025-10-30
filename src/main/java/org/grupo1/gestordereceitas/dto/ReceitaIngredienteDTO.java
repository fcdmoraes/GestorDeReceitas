package org.grupo1.gestordereceitas.dto;

public class ReceitaIngredienteDTO {

    private Long ingredienteId; // usado para request
    private String ingredienteNome; // usado no response
    private String quantidade;

    public Long getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Long ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getIngredienteNome() {
        return ingredienteNome;
    }

    public void setIngredienteNome(String ingredienteNome) {
        this.ingredienteNome = ingredienteNome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}