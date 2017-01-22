package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.DesenpenoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Desenpeno and its DTO DesenpenoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DesenpenoMapper {

    @Mapping(source = "jugador.id", target = "jugadorId")
    @Mapping(source = "jugador.cedula", target = "jugadorCedula")
    @Mapping(source = "entrenamiento.id", target = "entrenamientoId")
    @Mapping(source = "entrenamiento.descripcion", target = "entrenamientoDescripcion")
    DesenpenoDTO desenpenoToDesenpenoDTO(Desenpeno desenpeno);

    List<DesenpenoDTO> desenpenosToDesenpenoDTOs(List<Desenpeno> desenpenos);

    @Mapping(source = "jugadorId", target = "jugador")
    @Mapping(source = "entrenamientoId", target = "entrenamiento")
    Desenpeno desenpenoDTOToDesenpeno(DesenpenoDTO desenpenoDTO);

    List<Desenpeno> desenpenoDTOsToDesenpenos(List<DesenpenoDTO> desenpenoDTOs);

    default Jugador jugadorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Jugador jugador = new Jugador();
        jugador.setId(id);
        return jugador;
    }

    default Entrenamiento entrenamientoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setId(id);
        return entrenamiento;
    }
}
