package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Horario;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;
import com.cenfotec.examen1.domain.enumeration.Dia;

/**
 * Spring Data JPA repository for the Horario entity.
 */
@SuppressWarnings("unused")
public interface HorarioRepository extends JpaRepository<Horario,Long> {

    @Query("select u from Horario u " +
           "where u.horaInicio in (select min(ue.horaInicio) " +
                                  "from Horario ue " +
                                  "where ue.horaInicio >= ?1 " +
                                        "and ue.dia = ?2)")

    Optional<Horario> findMinHorarioByDia(int horaInicio, Dia Dia);


    @Query(value = "select h.* from horario h inner join " +
        "(select dia, MIN(hora_inicio) as  min_hora " +
        "from horario group by dia) as r " +
        "on r.dia = h.dia and r.min_hora =  h.hora_inicio", nativeQuery = true)
    List<Horario> findMinHorarios();
}
