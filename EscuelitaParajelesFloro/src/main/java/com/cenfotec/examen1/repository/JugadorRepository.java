package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Jugador;
import com.cenfotec.examen1.domain.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
public interface JugadorRepository extends JpaRepository<Jugador,Long> {
    Integer countByCategoriaId(Long id);
    Stream<Jugador> findByCategoria_Id(Long id);
}
