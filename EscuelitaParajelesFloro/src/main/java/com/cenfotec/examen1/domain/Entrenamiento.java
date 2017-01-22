package com.cenfotec.examen1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Entrenamiento.
 */
@Entity
@Table(name = "entrenamiento")
public class Entrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private ZonedDateTime fechaHora;

    @OneToMany(mappedBy = "entrenamiento")
    @JsonIgnore
    private Set<Desenpeno> desenpenos = new HashSet<>();

    /**
     * Relacion con el usuario built-in
     * que seria el entrenador (Verificar que sirva)
     */
    @ApiModelProperty(value = "Relacion con el usuario built-in que seria el entrenador (Verificar que sirva)")
    @ManyToOne
    private User entrenador;

    @ManyToOne
    private Horario horario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Entrenamiento descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFechaHora() {
        return fechaHora;
    }

    public Entrenamiento fechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public void setFechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Set<Desenpeno> getDesenpenos() {
        return desenpenos;
    }

    public Entrenamiento desenpenos(Set<Desenpeno> desenpenos) {
        this.desenpenos = desenpenos;
        return this;
    }

    public Entrenamiento addDesenpeno(Desenpeno desenpeno) {
        desenpenos.add(desenpeno);
        desenpeno.setEntrenamiento(this);
        return this;
    }

    public Entrenamiento removeDesenpeno(Desenpeno desenpeno) {
        desenpenos.remove(desenpeno);
        desenpeno.setEntrenamiento(null);
        return this;
    }

    public void setDesenpenos(Set<Desenpeno> desenpenos) {
        this.desenpenos = desenpenos;
    }

    public User getEntrenador() {
        return entrenador;
    }

    public Entrenamiento entrenador(User user) {
        this.entrenador = user;
        return this;
    }

    public void setEntrenador(User user) {
        this.entrenador = user;
    }

    public Horario getHorario() {
        return horario;
    }

    public Entrenamiento horario(Horario horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entrenamiento entrenamiento = (Entrenamiento) o;
        if (entrenamiento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entrenamiento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entrenamiento{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            ", fechaHora='" + fechaHora + "'" +
            '}';
    }
}
