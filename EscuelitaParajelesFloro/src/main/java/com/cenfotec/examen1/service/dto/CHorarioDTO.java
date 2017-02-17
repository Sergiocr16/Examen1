package com.cenfotec.examen1.service.dto;

import java.io.Serializable;
import com.cenfotec.examen1.service.dto.HorarioDTO;

import javax.validation.constraints.NotNull;

/**
 * Created by melvin on 1/23/2017.
 */
public class CHorarioDTO implements Serializable {

    @NotNull
    private HorarioDTO horario;

    @NotNull
    private Integer cantidadJugadores;

    public CHorarioDTO(HorarioDTO horario, Integer cantidadJugadores) {
        this.setHorario(horario);
        this.setCantidadJugadores(cantidadJugadores);
    }

    public HorarioDTO getHorario() {
        return horario;
    }

    public void setHorario(HorarioDTO horario) {
        this.horario = horario;
    }

    public Integer getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(Integer cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }
}
