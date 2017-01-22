package com.cenfotec.examen1.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Desenpeno.
 */
@Entity
@Table(name = "desenpeno")
public class Desenpeno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "notas")
    private String notas;

    @ManyToOne
    private Jugador jugador;

    @ManyToOne
    private Entrenamiento entrenamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public Desenpeno calificacion(Integer calificacion) {
        this.calificacion = calificacion;
        return this;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getNotas() {
        return notas;
    }

    public Desenpeno notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Desenpeno jugador(Jugador jugador) {
        this.jugador = jugador;
        return this;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public Desenpeno entrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
        return this;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Desenpeno desenpeno = (Desenpeno) o;
        if (desenpeno.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, desenpeno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Desenpeno{" +
            "id=" + id +
            ", calificacion='" + calificacion + "'" +
            ", notas='" + notas + "'" +
            '}';
    }
}
