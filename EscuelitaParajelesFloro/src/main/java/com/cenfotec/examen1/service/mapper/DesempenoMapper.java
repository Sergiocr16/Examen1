package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.DesempenoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Desempeno and its DTO DesempenoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DesempenoMapper {

    @Mapping(source = "jugador.id", target = "jugadorId")
    @Mapping(source = "jugador.cedula", target = "jugadorCedula")
    @Mapping(source = "entrenamiento.id", target = "entrenamientoId")
    @Mapping(source = "entrenamiento.descripcion", target = "entrenamientoDescripcion")
    DesempenoDTO desempenoToDesempenoDTO(Desempeno desempeno);

    List<DesempenoDTO> desempenosToDesempenoDTOs(List<Desempeno> desempenos);

    @Mapping(source = "jugadorId", target = "jugador")
    @Mapping(source = "entrenamientoId", target = "entrenamiento")
    Desempeno desempenoDTOToDesempeno(DesempenoDTO desempenoDTO);

    List<Desempeno> desempenoDTOsToDesempenos(List<DesempenoDTO> desempenoDTOs);

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
