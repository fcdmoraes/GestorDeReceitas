package org.grupo1.gestordereceitas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo1.gestordereceitas.config.SecurityConfig;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.service.IngredienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredienteController.class)
@Import(SecurityConfig.class)
public class IngredienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredienteService ingredienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarTodos_DeveRetornarListaDeIngredientes() throws Exception {
        Ingrediente ingrediente1 = new Ingrediente();
        ingrediente1.setId(1L);
        ingrediente1.setNome("Açúcar");

        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setId(2L);
        ingrediente2.setNome("Sal");

        List<Ingrediente> ingredientes = Arrays.asList(ingrediente1, ingrediente2);

        when(ingredienteService.listarTodos()).thenReturn(ingredientes);

        mockMvc.perform(get("/ingredientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Açúcar"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Sal"));
    }

    @Test
    public void buscarPorId_QuandoIngredienteExiste_DeveRetornarIngrediente() throws Exception {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(1L);
        ingrediente.setNome("Açúcar");

        when(ingredienteService.buscarPorId(1L)).thenReturn(ingrediente);

        mockMvc.perform(get("/ingredientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Açúcar"));
    }

    @Test
    public void criarIngrediente_QuandoDadosValidos_DeveCriarERetornarIngrediente() throws Exception {
        Ingrediente ingredienteRequest = new Ingrediente();
        ingredienteRequest.setNome("Novo Ingrediente");

        Ingrediente ingredienteSalvo = new Ingrediente();
        ingredienteSalvo.setId(1L);
        ingredienteSalvo.setNome("Novo Ingrediente");

        when(ingredienteService.salvar(any(Ingrediente.class))).thenReturn(ingredienteSalvo);

        mockMvc.perform(post("/ingredientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredienteRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Novo Ingrediente"));
    }

    @Test
    public void atualizarIngrediente_QuandoDadosValidos_DeveAtualizarERetornarIngrediente() throws Exception {
        Ingrediente ingredienteRequest = new Ingrediente();
        ingredienteRequest.setNome("Ingrediente Atualizado");

        Ingrediente ingredienteAtualizado = new Ingrediente();
        ingredienteAtualizado.setId(1L);
        ingredienteAtualizado.setNome("Ingrediente Atualizado");

        when(ingredienteService.atualizar(eq(1L), any(Ingrediente.class))).thenReturn(ingredienteAtualizado);

        mockMvc.perform(put("/ingredientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredienteRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Ingrediente Atualizado"));
    }

    @Test
    public void deletarIngrediente_QuandoIngredienteExiste_DeveRetornarNoContent() throws Exception {
        doNothing().when(ingredienteService).deletar(1L);

        mockMvc.perform(delete("/ingredientes/1"))
                .andExpect(status().isOk());
    }
}