package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.JugadorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Jugador and its DTO JugadorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JugadorMapper {

    @Mapping(source = "responsable.id", target = "responsableId")
    @Mapping(source = "responsable.email", target = "responsableEmail")
    JugadorDTO jugadorToJugadorDTO(Jugador jugador);

    List<JugadorDTO> jugadorsToJugadorDTOs(List<Jugador> jugadors);

    @Mapping(source = "responsableId", target = "responsable")
    Jugador jugadorDTOToJugador(JugadorDTO jugadorDTO);

    List<Jugador> jugadorDTOsToJugadors(List<JugadorDTO> jugadorDTOs);

    default Responsable responsableFromId(Long id) {
        if (id == null) {
            return null;
        }
        Responsable responsable = new Responsable();
        responsable.setId(id);
        return responsable;
    }
}
