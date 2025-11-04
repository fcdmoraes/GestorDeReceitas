package org.grupo1.gestordereceitas.repository;

import org.grupo1.gestordereceitas.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

/*    findAll()	Busca todas as receitas
    findById(Long id)	Busca uma receita pelo ID
    save(Receita receita)	Salva (ou atualiza) uma receita
    deleteById(Long id)	Deleta uma receita pelo ID
    count()	Conta quantos registros existem
    existsById(Long id)	Verifica se existe um registro com aquele ID*/
}
