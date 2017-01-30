package com.cenfotec.examen1.service.dto;

import java.time.LocalDate;
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
    private LocalDate fecha;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    private Integer horaInicio;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    private Integer horaFin;

    @NotNull
    private Integer categoria;


    private Long entrenadorId;
    

    private String entrenadorLogin;

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
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Integer getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }
    public Integer getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }
    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
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
            ", fecha='" + fecha + "'" +
            ", horaInicio='" + horaInicio + "'" +
            ", horaFin='" + horaFin + "'" +
            ", categoria='" + categoria + "'" +
            '}';
    }
}
