package com.cenfotec.examen1.service;

import com.cenfotec.examen1.domain.Jugador;
import com.cenfotec.examen1.repository.JugadorRepository;
import com.cenfotec.examen1.service.dto.JugadorDTO;
import com.cenfotec.examen1.service.mapper.JugadorMapper;
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
 * Service Implementation for managing Jugador.
 */
@Service
@Transactional
public class JugadorService {

    private final Logger log = LoggerFactory.getLogger(JugadorService.class);
    
    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private JugadorMapper jugadorMapper;

    /**
     * Save a jugador.
     *
     * @param jugadorDTO the entity to save
     * @return the persisted entity
     */
    public JugadorDTO save(JugadorDTO jugadorDTO) {
        log.debug("Request to save Jugador : {}", jugadorDTO);
        Jugador jugador = jugadorMapper.jugadorDTOToJugador(jugadorDTO);
        jugador = jugadorRepository.save(jugador);
        JugadorDTO result = jugadorMapper.jugadorToJugadorDTO(jugador);
        return result;
    }

    /**
     *  Get all the jugadors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<JugadorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Jugadors");
        Page<Jugador> result = jugadorRepository.findAll(pageable);
        return result.map(jugador -> jugadorMapper.jugadorToJugadorDTO(jugador));
    }

    /**
     *  Get one jugador by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public JugadorDTO findOne(Long id) {
        log.debug("Request to get Jugador : {}", id);
        Jugador jugador = jugadorRepository.findOne(id);
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);
        return jugadorDTO;
    }

    /**
     *  Delete the  jugador by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Jugador : {}", id);
        jugadorRepository.delete(id);
    }
}
