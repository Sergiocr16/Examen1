package com.cenfotec.examen1.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Desempeno entity.
 */
public class DesempenoDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer calificacion;

    private String notas;


    private Long jugadorId;
    

    private String jugadorCedula;

    private Long entrenamientoId;
    

    private String entrenamientoDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
    }


    public String getJugadorCedula() {
        return jugadorCedula;
    }

    public void setJugadorCedula(String jugadorCedula) {
        this.jugadorCedula = jugadorCedula;
    }

    public Long getEntrenamientoId() {
        return entrenamientoId;
    }

    public void setEntrenamientoId(Long entrenamientoId) {
        this.entrenamientoId = entrenamientoId;
    }


    public String getEntrenamientoDescripcion() {
        return entrenamientoDescripcion;
    }

    public void setEntrenamientoDescripcion(String entrenamientoDescripcion) {
        this.entrenamientoDescripcion = entrenamientoDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesempenoDTO desempenoDTO = (DesempenoDTO) o;

        if ( ! Objects.equals(id, desempenoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DesempenoDTO{" +
            "id=" + id +
            ", calificacion='" + calificacion + "'" +
            ", notas='" + notas + "'" +
            '}';
    }
}
