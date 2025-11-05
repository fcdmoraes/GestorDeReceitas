package org.grupo1.gestordereceitas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo1.gestordereceitas.config.SecurityConfig;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.service.CategoriaService;
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

@WebMvcTest(CategoriaController.class)
@Import(SecurityConfig.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarTodas_DeveRetornarListaDeCategorias() throws Exception {
        // Arrange
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Sobremesas");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Massas");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

        when(categoriaService.listarTodas()).thenReturn(categorias);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Sobremesas"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Massas"));
    }

    @Test
    public void buscarPorId_QuandoCategoriaExiste_DeveRetornarCategoria() throws Exception {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Sobremesas");

        when(categoriaService.buscarPorId(1L)).thenReturn(categoria);

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Sobremesas"));
    }

    @Test
    public void criarCategoria_QuandoDadosValidos_DeveCriarERetornarCategoria() throws Exception {
        // Arrange
        Categoria categoriaRequest = new Categoria();
        categoriaRequest.setNome("Nova Categoria");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(1L);
        categoriaSalva.setNome("Nova Categoria");

        when(categoriaService.salvar(any(Categoria.class))).thenReturn(categoriaSalva);

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Nova Categoria"));
    }

    @Test
    public void atualizarCategoria_QuandoDadosValidos_DeveAtualizarERetornarCategoria() throws Exception {
        // Arrange
        Categoria categoriaRequest = new Categoria();
        categoriaRequest.setNome("Categoria Atualizada");

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setId(1L);
        categoriaAtualizada.setNome("Categoria Atualizada");

        when(categoriaService.atualizar(eq(1L), any(Categoria.class))).thenReturn(categoriaAtualizada);

        mockMvc.perform(put("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Categoria Atualizada"));
    }

    @Test
    public void deletarCategoria_QuandoCategoriaExiste_DeveRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(categoriaService).deletar(1L);

        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isOk());
    }
}