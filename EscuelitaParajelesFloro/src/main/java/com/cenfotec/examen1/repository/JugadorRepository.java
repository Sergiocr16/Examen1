package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Jugador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
public interface JugadorRepository extends JpaRepository<Jugador,Long> {

}
