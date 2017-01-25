package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Desenpeno;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Desenpeno entity.
 */
@SuppressWarnings("unused")
public interface DesenpenoRepository extends JpaRepository<Desenpeno,Long> {
    Desenpeno findByEntrenamientoIdAndJugadorId(Long entrenamientoId, Long jugadorId);
}
