package com.cenfotec.examen1.service.CustomService;

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
import java.util.stream.IntStream;

/**
 * Created by melvin on 1/23/2017.
 */
@Service
@Transactional
public class CHorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioService.class);

    @Inject
    private HorarioRepository horarioRepository;
    @Inject
    private  JugadorRepository jugadorRepository;

    @Inject
    private HorarioMapper horarioMapper;

    @Transactional(readOnly = true)
    public Optional<HorarioDTO> horarioMasCercano() {
        LocalDateTime dt = LocalDateTime.now();
        int minutos      = dt.getHour() * 60 + dt.getMinute();
        int dayNum       = dt.getDayOfWeek().getValue();
        Dia[] dias       = Dia.values();

        Optional<Horario> r = horarioRepository.findMinHorarioByDia(minutos, dias[dayNum - 1]);
        if(r.isPresent()) {
            return r.map(horarioMapper::horarioToHorarioDTO);
        }

        Map<Dia, Horario> map = horarioRepository.findMinHorarios().stream()
            .collect(HashMap::new,
                (m, h) -> m.put(h.getDia(), h),
                HashMap::putAll);

        return IntStream
            .iterate(dayNum, i -> (i + 1) % 7)
            .mapToObj(i -> dias[i])
            .limit(7)
            .map(map::get)
            .filter(h -> h != null)
            .findFirst()
            .map(horarioMapper::horarioToHorarioDTO);
    }

    @Transactional(readOnly = true)
    public Optional<CHorarioDTO> horarioMasCercanoYJugadores() {
        return horarioMasCercano().map( h ->
            new CHorarioDTO(h, jugadorRepository.countByCategoriaId(h.getCategoriaId())));
    }
}
