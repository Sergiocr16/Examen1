package com.cenfotec.examen1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.cenfotec.examen1.domain.enumeration.Dia;

/**
 * Hay que preguntar si cada horario deberia tener un
 * entrenador asignado.
 * Asi solo se mostraria el horario mas cercano asignado
 * a este, cuando inicie session
 */
@ApiModel(description = "Hay que preguntar si cada horario deberia tener un entrenador asignado. Asi solo se mostraria el horario mas cercano asignado a este, cuando inicie session")
@Entity
@Table(name = "horario")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * horaInicio y hora fin son la cantidad
     * de minutos despues de media noche
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    @ApiModelProperty(value = "horaInicio y hora fin son la cantidad de minutos despues de media noche", required = true)
    @Column(name = "hora_inicio", nullable = false)
    private Integer horaInicio;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    @Column(name = "hora_fin", nullable = false)
    private Integer horaFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false)
    private Dia dia;

    @OneToMany(mappedBy = "horario")
    @JsonIgnore
    private Set<Entrenamiento> entrenamientos = new HashSet<>();

    @ManyToOne
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public Horario horaInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getHoraFin() {
        return horaFin;
    }

    public Horario horaFin(Integer horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }

    public Dia getDia() {
        return dia;
    }

    public Horario dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Set<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }

    public Horario entrenamientos(Set<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
        return this;
    }

    public Horario addEntrenamientos(Entrenamiento entrenamiento) {
        entrenamientos.add(entrenamiento);
        entrenamiento.setHorario(this);
        return this;
    }

    public Horario removeEntrenamientos(Entrenamiento entrenamiento) {
        entrenamientos.remove(entrenamiento);
        entrenamiento.setHorario(null);
        return this;
    }

    public void setEntrenamientos(Set<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Horario categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
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
        Horario horario = (Horario) o;
        if (horario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, horario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Horario{" +
            "id=" + id +
            ", horaInicio='" + horaInicio + "'" +
            ", horaFin='" + horaFin + "'" +
            ", dia='" + dia + "'" +
            '}';
    }
}
