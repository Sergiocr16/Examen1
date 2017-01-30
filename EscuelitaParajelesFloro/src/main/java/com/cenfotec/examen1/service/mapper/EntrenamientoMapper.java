package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.EntrenamientoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Entrenamiento and its DTO EntrenamientoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface EntrenamientoMapper {

    @Mapping(source = "entrenador.id", target = "entrenadorId")
    @Mapping(source = "entrenador.login", target = "entrenadorLogin")
    EntrenamientoDTO entrenamientoToEntrenamientoDTO(Entrenamiento entrenamiento);

    List<EntrenamientoDTO> entrenamientosToEntrenamientoDTOs(List<Entrenamiento> entrenamientos);

    @Mapping(target = "desenpenos", ignore = true)
    @Mapping(source = "entrenadorId", target = "entrenador")
    Entrenamiento entrenamientoDTOToEntrenamiento(EntrenamientoDTO entrenamientoDTO);

    List<Entrenamiento> entrenamientoDTOsToEntrenamientos(List<EntrenamientoDTO> entrenamientoDTOs);
}
