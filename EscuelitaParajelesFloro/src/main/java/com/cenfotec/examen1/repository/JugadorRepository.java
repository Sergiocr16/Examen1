package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Jugador;
import com.cenfotec.examen1.domain.Categoria;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
public interface JugadorRepository extends JpaRepository<Jugador,Long> {
    Integer countByCategoriaId(Long id);
}
