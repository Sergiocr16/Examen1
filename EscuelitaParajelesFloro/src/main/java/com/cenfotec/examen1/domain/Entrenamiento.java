package com.cenfotec.examen1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    @Column(name = "hora_inicio", nullable = false)
    private Integer horaInicio;

    @NotNull
    @Min(value = 0)
    @Max(value = 1439)
    @Column(name = "hora_fin", nullable = false)
    private Integer horaFin;

    @NotNull
    @Column(name = "categoria", nullable = false)
    private Integer categoria;

    @OneToMany(mappedBy = "entrenamiento")
    @JsonIgnore
    private Set<Desempeno> desenpenos = new HashSet<>();

    @ManyToOne
    private User entrenador;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public Entrenamiento fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public Entrenamiento horaInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getHoraFin() {
        return horaFin;
    }

    public Entrenamiento horaFin(Integer horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public Entrenamiento categoria(Integer categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Set<Desempeno> getDesenpenos() {
        return desenpenos;
    }

    public Entrenamiento desenpenos(Set<Desempeno> desempenos) {
        this.desenpenos = desempenos;
        return this;
    }

    public Entrenamiento addDesenpeno(Desempeno desempeno) {
        desenpenos.add(desempeno);
        desempeno.setEntrenamiento(this);
        return this;
    }

    public Entrenamiento removeDesenpeno(Desempeno desempeno) {
        desenpenos.remove(desempeno);
        desempeno.setEntrenamiento(null);
        return this;
    }

    public void setDesenpenos(Set<Desempeno> desempenos) {
        this.desenpenos = desempenos;
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
            ", fecha='" + fecha + "'" +
            ", horaInicio='" + horaInicio + "'" +
            ", horaFin='" + horaFin + "'" +
            ", categoria='" + categoria + "'" +
            '}';
    }
}
