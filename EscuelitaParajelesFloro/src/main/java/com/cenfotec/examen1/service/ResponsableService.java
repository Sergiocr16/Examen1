package com.cenfotec.examen1.service;

import com.cenfotec.examen1.domain.Responsable;
import com.cenfotec.examen1.repository.ResponsableRepository;
import com.cenfotec.examen1.service.dto.ResponsableDTO;
import com.cenfotec.examen1.service.mapper.ResponsableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Responsable.
 */
@Service
@Transactional
public class ResponsableService {

    private final Logger log = LoggerFactory.getLogger(ResponsableService.class);
    
    @Inject
    private ResponsableRepository responsableRepository;

    @Inject
    private ResponsableMapper responsableMapper;

    /**
     * Save a responsable.
     *
     * @param responsableDTO the entity to save
     * @return the persisted entity
     */
    public ResponsableDTO save(ResponsableDTO responsableDTO) {
        log.debug("Request to save Responsable : {}", responsableDTO);
        Responsable responsable = responsableMapper.responsableDTOToResponsable(responsableDTO);
        responsable = responsableRepository.save(responsable);
        ResponsableDTO result = responsableMapper.responsableToResponsableDTO(responsable);
        return result;
    }

    /**
     *  Get all the responsables.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ResponsableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsables");
        Page<Responsable> result = responsableRepository.findAll(pageable);
        return result.map(responsable -> responsableMapper.responsableToResponsableDTO(responsable));
    }

    /**
     *  Get one responsable by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ResponsableDTO findOne(Long id) {
        log.debug("Request to get Responsable : {}", id);
        Responsable responsable = responsableRepository.findOne(id);
        ResponsableDTO responsableDTO = responsableMapper.responsableToResponsableDTO(responsable);
        return responsableDTO;
    }

    /**
     *  Delete the  responsable by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Responsable : {}", id);
        responsableRepository.delete(id);
    }
}
