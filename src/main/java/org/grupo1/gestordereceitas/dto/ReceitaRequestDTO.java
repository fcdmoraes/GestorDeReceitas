package org.grupo1.gestordereceitas.dto;

import java.util.List;

public class ReceitaRequestDTO {

    private String nome;
    private String descricao;
    private int tempoDePreparo;
    private Long categoriaID;
    private List<ReceitaIngredienteDTO> ingredientes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTempoDePreparo() {
        return tempoDePreparo;
    }

    public void setTempoDePreparo(int tempoDePreparo) {
        this.tempoDePreparo = tempoDePreparo;
    }

    public Long getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(Long categoriaID) {
        this.categoriaID = categoriaID;
    }

    public List<ReceitaIngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ReceitaIngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }

}
