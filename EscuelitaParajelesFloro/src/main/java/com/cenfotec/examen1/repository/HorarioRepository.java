package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Horario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Horario entity.
 */
@SuppressWarnings("unused")
public interface HorarioRepository extends JpaRepository<Horario,Long> {

}
