package com.cenfotec.examen1.service;

import com.cenfotec.examen1.domain.Desenpeno;
import com.cenfotec.examen1.repository.DesenpenoRepository;
import com.cenfotec.examen1.service.dto.DesenpenoDTO;
import com.cenfotec.examen1.service.mapper.DesenpenoMapper;
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
 * Service Implementation for managing Desenpeno.
 */
@Service
@Transactional
public class DesenpenoService {

    private final Logger log = LoggerFactory.getLogger(DesenpenoService.class);

    @Inject
    private DesenpenoRepository desenpenoRepository;

    @Inject
    private DesenpenoMapper desenpenoMapper;

    /**
     * Save a desenpeno.
     *
     * @param desenpenoDTO the entity to save
     * @return the persisted entity
     */
    public DesenpenoDTO save(DesenpenoDTO desenpenoDTO) {
        log.debug("Request to save Desenpeno : {}", desenpenoDTO);
        Desenpeno desenpeno = desenpenoMapper.desenpenoDTOToDesenpeno(desenpenoDTO);
        desenpeno = desenpenoRepository.save(desenpeno);
        DesenpenoDTO result = desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno);
        return result;
    }

    /**
     *  Get all the desenpenos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesenpenoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Desenpenos");
        Page<Desenpeno> result = desenpenoRepository.findAll(pageable);
        return result.map(desenpeno -> desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno));
    }

    /**
     *  Get one desenpeno by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DesenpenoDTO findByEntrenamientoIdAndJugadorId(Long entrenamientoId,Long jugadorId) {
//        log.debug("Request to get Desenpeno : {}", id);
        Desenpeno desenpeno = desenpenoRepository.findByEntrenamientoIdAndJugadorId(entrenamientoId,jugadorId);
        DesenpenoDTO desenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno);
        return desenpenoDTO;
    }

    @Transactional(readOnly = true)
    public DesenpenoDTO findOne(Long id) {
        log.debug("Request to get Desenpeno : {}", id);
        Desenpeno desenpeno = desenpenoRepository.findOne(id);
        DesenpenoDTO desenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno);
        return desenpenoDTO;
    }

    /**
     *  Delete the  desenpeno by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Desenpeno : {}", id);
        desenpenoRepository.delete(id);
    }
}
