package com.cenfotec.examen1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Min(value = 0)
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private Set<Jugador> jugadores = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public Categoria edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public Categoria horarios(Set<Horario> horarios) {
        this.horarios = horarios;
        return this;
    }

    public Categoria addHorarios(Horario horario) {
        horarios.add(horario);
        horario.setCategoria(this);
        return this;
    }

    public Categoria removeHorarios(Horario horario) {
        horarios.remove(horario);
        horario.setCategoria(null);
        return this;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public Set<Jugador> getJugadores() {
        return jugadores;
    }

    public Categoria jugadores(Set<Jugador> jugadors) {
        this.jugadores = jugadors;
        return this;
    }

    public Categoria addJugadores(Jugador jugador) {
        jugadores.add(jugador);
        jugador.setCategoria(this);
        return this;
    }

    public Categoria removeJugadores(Jugador jugador) {
        jugadores.remove(jugador);
        jugador.setCategoria(null);
        return this;
    }

    public void setJugadores(Set<Jugador> jugadors) {
        this.jugadores = jugadors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categoria categoria = (Categoria) o;
        if (categoria.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", edad='" + edad + "'" +
            '}';
    }
}
