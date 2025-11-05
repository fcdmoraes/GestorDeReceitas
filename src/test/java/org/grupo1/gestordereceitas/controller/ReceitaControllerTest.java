package org.grupo1.gestordereceitas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo1.gestordereceitas.config.SecurityConfig;
import org.grupo1.gestordereceitas.dto.ReceitaIngredienteDTO;
import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.service.ReceitaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceitaController.class)
@Import(SecurityConfig.class)
public class ReceitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceitaService receitaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarTodas_DeveRetornarListaDeReceitas() throws Exception {
        // Arrange
        ReceitaResponseDTO receita1 = new ReceitaResponseDTO();
        receita1.setId(1L);
        receita1.setNome("Bolo de Chocolate");
        receita1.setDescricao("Bolo simples de chocolate");
        receita1.setTempoDePreparo(45);
        receita1.setCategoria("Sobremesas");
        receita1.setIngredientes(Collections.emptyList());

        ReceitaResponseDTO receita2 = new ReceitaResponseDTO();
        receita2.setId(2L);
        receita2.setNome("Pão de Queijo");
        receita2.setDescricao("Pão de queijo mineiro");
        receita2.setTempoDePreparo(30);
        receita2.setCategoria("Lanches");
        receita2.setIngredientes(Collections.emptyList());

        List<ReceitaResponseDTO> receitas = Arrays.asList(receita1, receita2);

        when(receitaService.listarTodas()).thenReturn(receitas);

        // Act & Assert
        mockMvc.perform(get("/receitas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Bolo de Chocolate"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Pão de Queijo"));
    }

    @Test
    public void buscarPorId_QuandoReceitaExiste_DeveRetornarReceita() throws Exception {
        // Arrange
        ReceitaResponseDTO receita = new ReceitaResponseDTO();
        receita.setId(1L);
        receita.setNome("Bolo de Chocolate");
        receita.setDescricao("Bolo simples de chocolate");
        receita.setTempoDePreparo(45);
        receita.setCategoria("Sobremesas");
        receita.setIngredientes(Collections.emptyList());

        when(receitaService.buscarPorId(1L)).thenReturn(receita);

        // Act & Assert
        mockMvc.perform(get("/receitas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Bolo de Chocolate"));
    }

    @Test
    public void criarReceita_QuandoDadosValidos_DeveCriarERetornarReceita() throws Exception {
        // Arrange
        ReceitaRequestDTO receitaRequest = new ReceitaRequestDTO();
        receitaRequest.setNome("Nova Receita");
        receitaRequest.setDescricao("Descrição da nova receita");
        receitaRequest.setTempoDePreparo(30);
        receitaRequest.setCategoriaId(1L);
        receitaRequest.setIngredientes(Collections.emptyList());

        ReceitaResponseDTO receitaResponse = new ReceitaResponseDTO();
        receitaResponse.setId(1L);
        receitaResponse.setNome("Nova Receita");
        receitaResponse.setDescricao("Descrição da nova receita");
        receitaResponse.setTempoDePreparo(30);
        receitaResponse.setCategoria("Sobremesas");
        receitaResponse.setIngredientes(Collections.emptyList());

        when(receitaService.salvar(any(ReceitaRequestDTO.class))).thenReturn(receitaResponse);

        // Act & Assert
        mockMvc.perform(post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receitaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Nova Receita"));
    }

    @Test
    public void atualizarReceita_QuandoDadosValidos_DeveAtualizarERetornarReceita() throws Exception {
        // Arrange
        ReceitaRequestDTO receitaRequest = new ReceitaRequestDTO();
        receitaRequest.setNome("Receita Atualizada");
        receitaRequest.setDescricao("Descrição atualizada");
        receitaRequest.setTempoDePreparo(45);
        receitaRequest.setCategoriaId(1L);
        receitaRequest.setIngredientes(Collections.emptyList());

        ReceitaResponseDTO receitaResponse = new ReceitaResponseDTO();
        receitaResponse.setId(1L);
        receitaResponse.setNome("Receita Atualizada");
        receitaResponse.setDescricao("Descrição atualizada");
        receitaResponse.setTempoDePreparo(45);
        receitaResponse.setCategoria("Sobremesas");
        receitaResponse.setIngredientes(Collections.emptyList());

        when(receitaService.atualizar(eq(1L), any(ReceitaRequestDTO.class))).thenReturn(receitaResponse);

        // Act & Assert
        mockMvc.perform(put("/receitas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receitaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Receita Atualizada"));
    }

    @Test
    public void atualizarParcialReceita_QuandoDadosValidos_DeveAtualizarERetornarReceita() throws Exception {
        // Arrange
        ReceitaRequestDTO receitaRequest = new ReceitaRequestDTO();
        receitaRequest.setNome("Nome Atualizado");

        ReceitaResponseDTO receitaResponse = new ReceitaResponseDTO();
        receitaResponse.setId(1L);
        receitaResponse.setNome("Nome Atualizado");
        receitaResponse.setDescricao("Descrição original");
        receitaResponse.setTempoDePreparo(30);
        receitaResponse.setCategoria("Sobremesas");
        receitaResponse.setIngredientes(Collections.emptyList());

        when(receitaService.atualizarParcial(eq(1L), any(ReceitaRequestDTO.class))).thenReturn(receitaResponse);

        // Act & Assert
        mockMvc.perform(patch("/receitas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receitaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
    }

    @Test
    public void deletarReceita_QuandoReceitaExiste_DeveRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(receitaService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/receitas/1"))
                .andExpect(status().isOk());
    }
}