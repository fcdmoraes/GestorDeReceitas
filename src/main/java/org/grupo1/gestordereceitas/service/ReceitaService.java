package org.grupo1.gestordereceitas.service;

import org.grupo1.gestordereceitas.dto.ReceitaRequestDTO;
import org.grupo1.gestordereceitas.dto.ReceitaResponseDTO;
import org.grupo1.gestordereceitas.dto.mapper.ReceitaMapper;
import org.grupo1.gestordereceitas.exception.BusinessException;
import org.grupo1.gestordereceitas.exception.ResourceNotFoundException;
import org.grupo1.gestordereceitas.model.Categoria;
import org.grupo1.gestordereceitas.model.Ingrediente;
import org.grupo1.gestordereceitas.model.Receita;
import org.grupo1.gestordereceitas.repository.CategoriaRepository;
import org.grupo1.gestordereceitas.repository.IngredienteRepository;
import org.grupo1.gestordereceitas.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final CategoriaRepository categoriaRepository;
    private final IngredienteRepository ingredienteRepository;

    public ReceitaService(ReceitaRepository receitaRepository,
                          CategoriaRepository categoriaRepository,
                          IngredienteRepository ingredienteRepository) {
        this.receitaRepository = receitaRepository;
        this.categoriaRepository = categoriaRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    public ReceitaResponseDTO criarReceita(ReceitaRequestDTO dto) {
        // Busca categoria
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaID())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + dto.getCategoriaID()));

        // Busca todos os ingredientes do banco
        List<Ingrediente> ingredientesDisponiveis = ingredienteRepository.findAll();

        // Converte DTO em entidade
        Receita receita = ReceitaMapper.toEntity(dto, categoria, ingredientesDisponiveis);

        // Salva no banco
        Receita receitaSalva = receitaRepository.save(receita);

        // Retorna DTO de resposta
        return ReceitaMapper.toDTO(receitaSalva);
    }

    // Listar todas as receitas
    public List<ReceitaResponseDTO> listarTodas() {
        return ReceitaMapper.toDTOList(receitaRepository.findAll());
    }

    // Buscar por ID
    public ReceitaResponseDTO buscarPorId(Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita com ID " + id + " não encontrada"));
        return ReceitaMapper.toDTO(receita);
    }

    // Criar receita
    public ReceitaResponseDTO salvar(ReceitaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaID())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com ID " + dto.getCategoriaID() + " não encontrada"));

        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        if (ingredientes.isEmpty()) {
            throw new BusinessException("Nenhum ingrediente cadastrado.");
        }

        Receita receita = ReceitaMapper.toEntity(dto, categoria, ingredientes);
        Receita receitaSalva = receitaRepository.save(receita);
        return ReceitaMapper.toDTO(receitaSalva);
    }

    // Atualizar receita (PUT)
    public ReceitaResponseDTO atualizar(Long id, ReceitaRequestDTO dto) {
        Receita receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita com ID " + id + " não encontrada"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaID())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com ID " + dto.getCategoriaID() + " não encontrada"));

        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        Receita receitaAtualizada = ReceitaMapper.toEntity(dto, categoria, ingredientes);
        receitaAtualizada.setId(receitaExistente.getId()); // mantém o mesmo ID

        Receita receitaSalva = receitaRepository.save(receitaAtualizada);
        return ReceitaMapper.toDTO(receitaSalva);
    }

    // Atualizar parcialmente (PATCH)
    public ReceitaResponseDTO atualizarParcial(Long id, ReceitaRequestDTO dto) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita com ID " + id + " não encontrada"));

        if (dto.getNome() != null) receita.setNome(dto.getNome());
        if (dto.getDescricao() != null) receita.setDescricao(dto.getDescricao());
        if (dto.getTempoDePreparo() > 0) receita.setTempoDePreparo(dto.getTempoDePreparo());

        if (dto.getCategoriaID() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaID())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria com ID " + dto.getCategoriaID() + " não encontrada"));
            receita.setCategoria(categoria);
        }

        if (dto.getIngredientes() != null && !dto.getIngredientes().isEmpty()) {
            List<Ingrediente> ingredientes = ingredienteRepository.findAll();
            receita.getReceitaIngredientes().clear();
            receita.getReceitaIngredientes().addAll(
                    ReceitaMapper.toEntity(dto, receita.getCategoria(), ingredientes).getReceitaIngredientes()
            );
        }

        Receita receitaSalva = receitaRepository.save(receita);
        return ReceitaMapper.toDTO(receitaSalva);
    }

    // Deletar receita
    public void deletar(Long id) {
        if (!receitaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receita com ID " + id + " não encontrada para exclusão");
        }
        receitaRepository.deleteById(id);
    }
}
