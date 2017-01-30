package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.HorarioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Horario and its DTO HorarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HorarioMapper {

    HorarioDTO horarioToHorarioDTO(Horario horario);

    List<HorarioDTO> horariosToHorarioDTOs(List<Horario> horarios);

    Horario horarioDTOToHorario(HorarioDTO horarioDTO);

    List<Horario> horarioDTOsToHorarios(List<HorarioDTO> horarioDTOs);
}
