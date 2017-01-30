package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Responsable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Responsable entity.
 */
@SuppressWarnings("unused")
public interface ResponsableRepository extends JpaRepository<Responsable,Long> {

}
