package com.cenfotec.examen1.repository;

import com.cenfotec.examen1.domain.Horario;

import com.cenfotec.examen1.domain.enumeration.Dia;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    Stream<Horario> findMinHorarios();


    @Query("select u from Horario u " +
        "where u.horaFin > :#{#hor.horaInicio} and u.horaInicio < :#{#hor.horaFin} and u.dia = :#{#hor.dia}")
    Stream<Horario> findIntersectors(@Param("hor") Horario horario);
}
