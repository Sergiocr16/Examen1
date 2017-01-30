package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Desempeno;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Desempeno entity.
 */
@SuppressWarnings("unused")
public interface DesempenoRepository extends JpaRepository<Desempeno,Long> {

}
