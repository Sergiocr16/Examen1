package com.cenfotec.examen1.service;

import com.cenfotec.examen1.domain.Horario;
import com.cenfotec.examen1.repository.HorarioRepository;
import com.cenfotec.examen1.service.dto.HorarioDTO;
import com.cenfotec.examen1.service.mapper.HorarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.Optional;
/**
 * Service Implementation for managing Horario.
 */
@Service
@Transactional
public class HorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioService.class);

    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private HorarioMapper horarioMapper;

    /**
     * Save a horario.
     *
     * @param horarioDTO the entity to save
     * @return the persisted entity
     */
    public HorarioDTO save(HorarioDTO horarioDTO) {
        log.debug("Request to save Horario : {}", horarioDTO);
        Horario horario = horarioMapper.horarioDTOToHorario(horarioDTO);
        horario = horarioRepository.save(horario);
        HorarioDTO result = horarioMapper.horarioToHorarioDTO(horario);
        return result;
    }

    /**
     *  Get all the horarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HorarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Horarios");
        Page<Horario> result = horarioRepository.findAll(pageable);
        return result.map(horario -> horarioMapper.horarioToHorarioDTO(horario));
    }

    /**
     *  Get one horario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HorarioDTO findOne(Long id) {
        log.debug("Request to get Horario : {}", id);
        Horario horario = horarioRepository.findOne(id);
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);
        return horarioDTO;
    }

    /**
     *  Delete the  horario by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Horario : {}", id);
        horarioRepository.delete(id);
    }

    public Optional<HorarioDTO> intersection(HorarioDTO h) {
        log.debug("fuckkkkkkkkkkkkk {}", h.getId());
        return horarioRepository
            .findIntersectors(horarioMapper.horarioDTOToHorario(h))
            .stream()
            .filter(hor -> !hor.getId().equals(h.getId()))
            .findAny()
            .map(horarioMapper::horarioToHorarioDTO);
    }
}
