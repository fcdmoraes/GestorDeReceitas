package org.grupo1.gestordereceitas.repository;

import org.grupo1.gestordereceitas.model.ReceitaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaIngredienteRepository extends JpaRepository<ReceitaIngrediente, Long> {
}
