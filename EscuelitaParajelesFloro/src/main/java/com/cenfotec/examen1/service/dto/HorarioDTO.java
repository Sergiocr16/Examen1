package com.cenfotec.examen1.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.cenfotec.examen1.constrains.HorarioDTOTimeConstrain;
import com.cenfotec.examen1.domain.enumeration.Dia;

/**
 * A DTO for the Horario entity.
 */
@HorarioDTOTimeConstrain(message = "HoraInicio debe ser menor a horaFin")
public class HorarioDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    private Integer horaInicio;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    private Integer horaFin;

    @NotNull
    private Dia dia;

    @NotNull
    private Integer categoria;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }
    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorarioDTO horarioDTO = (HorarioDTO) o;

        if ( ! Objects.equals(id, horarioDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HorarioDTOTimeConstrain{" +
            "id=" + id +
            ", horaInicio='" + horaInicio + "'" +
            ", horaFin='" + horaFin + "'" +
            ", dia='" + dia + "'" +
            ", categoria='" + categoria + "'" +
            '}';
    }
}
