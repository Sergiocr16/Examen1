package com.cenfotec.examen1.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Seria mejor tener una fecha de nacimiento
     * en lugar de edad pero asi no lo establece los requerimientos.
     */
    @NotNull
    @ApiModelProperty(value = "Seria mejor tener una fecha de nacimiento en lugar de edad pero asi no lo establece los requerimientos.", required = true)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Min(value = 0)
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotNull
    @Pattern(regexp = "^0?\\d(-?\\d{4}){2}$")
    @Column(name = "cedula", nullable = false)
    private String cedula;

    @Column(name = "posicion")
    private String posicion;

    @ManyToOne
    private Responsable responsable;

    @ManyToOne
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Jugador apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public Jugador edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCedula() {
        return cedula;
    }

    public Jugador cedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPosicion() {
        return posicion;
    }

    public Jugador posicion(String posicion) {
        this.posicion = posicion;
        return this;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public Jugador responsable(Responsable responsable) {
        this.responsable = responsable;
        return this;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Jugador categoria(Categoria categoria) {
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
        Jugador jugador = (Jugador) o;
        if (jugador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Jugador{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", apellido='" + apellido + "'" +
            ", edad='" + edad + "'" +
            ", cedula='" + cedula + "'" +
            ", posicion='" + posicion + "'" +
            '}';
    }
}
