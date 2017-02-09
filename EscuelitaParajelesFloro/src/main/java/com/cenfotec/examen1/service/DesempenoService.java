package com.cenfotec.examen1.service;

import com.cenfotec.examen1.domain.Desempeno;
import com.cenfotec.examen1.repository.DesempenoRepository;
import com.cenfotec.examen1.service.dto.DesempenoDTO;
import com.cenfotec.examen1.service.mapper.DesempenoMapper;
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
 * Service Implementation for managing Desempeno.
 */
@Service
@Transactional
public class DesempenoService {

    private final Logger log = LoggerFactory.getLogger(DesempenoService.class);

    @Inject
    private DesempenoRepository desempenoRepository;

    @Inject
    private DesempenoMapper desempenoMapper;

    /**
     * Save a desempeno.
     *
     * @param desempenoDTO the entity to save
     * @return the persisted entity
     */
    public DesempenoDTO save(DesempenoDTO desempenoDTO) {
        log.debug("Request to save Desempeno : {}", desempenoDTO);
        Desempeno desempeno = desempenoMapper.desempenoDTOToDesempeno(desempenoDTO);
        desempeno = desempenoRepository.save(desempeno);
        DesempenoDTO result = desempenoMapper.desempenoToDesempenoDTO(desempeno);
        return result;
    }

    /**
     *  Get all the desempenos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesempenoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Desempenos");
        Page<Desempeno> result = desempenoRepository.findAll(pageable);
        return result.map(desempeno -> desempenoMapper.desempenoToDesempenoDTO(desempeno));
    }
    @Transactional(readOnly = true)
    public DesempenoDTO findByEntrenamientoIdAndJugadorId(Long entrenamientoId,Long jugadorId) {
        //        log.debug("Request to get Desenpeno : {}", id);
                Desempeno desempeno = desempenoRepository.findByEntrenamientoIdAndJugadorId(entrenamientoId,jugadorId);
                DesempenoDTO desempenoDTO = desempenoMapper.desempenoToDesempenoDTO(desempeno);
        return desempenoDTO;
    }

    /**
     *  Get one desempeno by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DesempenoDTO findOne(Long id) {
        log.debug("Request to get Desempeno : {}", id);
        Desempeno desempeno = desempenoRepository.findOne(id);
        DesempenoDTO desempenoDTO = desempenoMapper.desempenoToDesempenoDTO(desempeno);
        return desempenoDTO;
    }

    /**
     *  Delete the  desempeno by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Desempeno : {}", id);
        desempenoRepository.delete(id);
    }
}
