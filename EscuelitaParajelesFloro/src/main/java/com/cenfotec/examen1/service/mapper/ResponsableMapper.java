package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.ResponsableDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Responsable and its DTO ResponsableDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResponsableMapper {

    ResponsableDTO responsableToResponsableDTO(Responsable responsable);

    List<ResponsableDTO> responsablesToResponsableDTOs(List<Responsable> responsables);

    Responsable responsableDTOToResponsable(ResponsableDTO responsableDTO);

    List<Responsable> responsableDTOsToResponsables(List<ResponsableDTO> responsableDTOs);
}
