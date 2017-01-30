package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Entrenamiento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Entrenamiento entity.
 */
@SuppressWarnings("unused")
public interface EntrenamientoRepository extends JpaRepository<Entrenamiento,Long> {

    @Query("select entrenamiento from Entrenamiento entrenamiento where entrenamiento.entrenador.login = ?#{principal.username}")
    List<Entrenamiento> findByEntrenadorIsCurrentUser();

}
