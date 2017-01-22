package com.cenfotec.examen1.service.mapper;

import com.cenfotec.examen1.domain.*;
import com.cenfotec.examen1.service.dto.CategoriaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper {

    CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);

    List<CategoriaDTO> categoriasToCategoriaDTOs(List<Categoria> categorias);

    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "jugadores", ignore = true)
    Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO);

    List<Categoria> categoriaDTOsToCategorias(List<CategoriaDTO> categoriaDTOs);
}
