package com.cenfotec.examen1.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Entrenamiento entity.
 */
public class EntrenamientoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private ZonedDateTime fechaHora;


    private Long entrenadorId;
    

    private String entrenadorLogin;

    private Long horarioId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public ZonedDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(Long userId) {
        this.entrenadorId = userId;
    }


    public String getEntrenadorLogin() {
        return entrenadorLogin;
    }

    public void setEntrenadorLogin(String userLogin) {
        this.entrenadorLogin = userLogin;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrenamientoDTO entrenamientoDTO = (EntrenamientoDTO) o;

        if ( ! Objects.equals(id, entrenamientoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EntrenamientoDTO{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            ", fechaHora='" + fechaHora + "'" +
            '}';
    }
}
