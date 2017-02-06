package com.cenfotec.examen1.service.customService;

import com.cenfotec.examen1.domain.Horario;
import com.cenfotec.examen1.domain.enumeration.Dia;
import com.cenfotec.examen1.repository.HorarioRepository;
import com.cenfotec.examen1.repository.JugadorRepository;
import com.cenfotec.examen1.service.HorarioService;
import com.cenfotec.examen1.service.dto.HorarioDTO;
import com.cenfotec.examen1.service.dto.customDTO.CHorarioDTO;
import com.cenfotec.examen1.service.mapper.HorarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by melvin on 1/23/2017.
 */
@Service
@Transactional
public class CHorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioService.class);

    private final int NUM_DIA  = 7;
    private final int MIN_IN_H = 60;

    @Inject
    private HorarioRepository horarioRepository;
    @Inject
    private  JugadorRepository jugadorRepository;

    @Inject
    private HorarioMapper horarioMapper;

    @Transactional(readOnly = true)
    public Optional<HorarioDTO> horarioMasCercano() {

        LocalDateTime dt = LocalDateTime.now();
        int minutos      = dt.getHour() * MIN_IN_H + dt.getMinute();
        int dayNum       = dt.getDayOfWeek().getValue();
        Dia[] dias       = Dia.values();

        Optional<Horario> r = horarioRepository.findMinHorarioByDia(minutos, dias[dayNum - 1]);
        if(r.isPresent()) {
            return r.map(horarioMapper::horarioToHorarioDTO);
        }

        Map<Dia, Horario> map = horarioRepository.findMinHorarios()
            .collect(Collectors.toMap(Horario::getDia, Function.identity()));
        return IntStream
            .iterate(dayNum % NUM_DIA, i -> (i + 1) % NUM_DIA)
            .mapToObj(i -> dias[i])
            .limit(NUM_DIA)
            .map(map::get)
            .filter(h -> h != null)
            .findFirst()
            .map(horarioMapper::horarioToHorarioDTO);
    }

    @Transactional(readOnly = true)
    public Optional<CHorarioDTO> horarioMasCercanoYJugadores() {
        return horarioMasCercano().map( h ->
            new CHorarioDTO(h, jugadorRepository.countByEdad(h.getCategoria())));
    }
}
