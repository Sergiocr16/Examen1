package com.cenfotec.examen1.web.customRest;



import com.cenfotec.examen1.service.dto.customDTO.CHorarioDTO;
import com.cenfotec.examen1.web.rest.HorarioResource;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import com.cenfotec.examen1.service.customService.CHorarioService;

/**
 * Created by melvin on 1/23/2017.
 */
@RestController
@RequestMapping("/api")
public class CHorarioResource {

    private final Logger log = LoggerFactory.getLogger(HorarioResource.class);

    @Inject
    private CHorarioService horarioService;

    @GetMapping("/horarioMasCercano")
    @Timed
    public ResponseEntity<CHorarioDTO> getHorarioMasCercano() {
        return horarioService.horarioMasCercanoYJugadores()
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
