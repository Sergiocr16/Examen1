package com.cenfotec.examen1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.examen1.service.JugadorService;
import com.cenfotec.examen1.web.rest.util.HeaderUtil;
import com.cenfotec.examen1.web.rest.util.PaginationUtil;
import com.cenfotec.examen1.service.dto.JugadorDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Jugador.
 */
@RestController
@RequestMapping("/api")
public class JugadorResource {

    private final Logger log = LoggerFactory.getLogger(JugadorResource.class);

    @Inject
    private JugadorService jugadorService;

    /**
     * POST  /jugadors : Create a new jugador.
     *
     * @param jugadorDTO the jugadorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jugadorDTO, or with status 400 (Bad Request) if the jugador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jugadors")
    @Timed
    public ResponseEntity<JugadorDTO> createJugador(@Valid @RequestBody JugadorDTO jugadorDTO) throws URISyntaxException {
        log.debug("REST request to save Jugador : {}", jugadorDTO);
        if (jugadorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jugador", "idexists", "A new jugador cannot already have an ID")).body(null);
        }
        JugadorDTO result = jugadorService.save(jugadorDTO);
        return ResponseEntity.created(new URI("/api/jugadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jugador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jugadors : Updates an existing jugador.
     *
     * @param jugadorDTO the jugadorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jugadorDTO,
     * or with status 400 (Bad Request) if the jugadorDTO is not valid,
     * or with status 500 (Internal Server Error) if the jugadorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jugadors")
    @Timed
    public ResponseEntity<JugadorDTO> updateJugador(@Valid @RequestBody JugadorDTO jugadorDTO) throws URISyntaxException {
        log.debug("REST request to update Jugador : {}", jugadorDTO);
        if (jugadorDTO.getId() == null) {
            return createJugador(jugadorDTO);
        }
        JugadorDTO result = jugadorService.save(jugadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jugador", jugadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jugadors : get all the jugadors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jugadors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/jugadors")
    @Timed
    public ResponseEntity<List<JugadorDTO>> getAllJugadors(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Jugadors");
        Page<JugadorDTO> page = jugadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jugadors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jugadors/:id : get the "id" jugador.
     *
     * @param id the id of the jugadorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jugadorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jugadors/{id}")
    @Timed
    public ResponseEntity<JugadorDTO> getJugador(@PathVariable Long id) {
        log.debug("REST request to get Jugador : {}", id);
        JugadorDTO jugadorDTO = jugadorService.findOne(id);
        return Optional.ofNullable(jugadorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jugadors/:id : delete the "id" jugador.
     *
     * @param id the id of the jugadorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jugadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteJugador(@PathVariable Long id) {
        log.debug("REST request to delete Jugador : {}", id);
        jugadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jugador", id.toString())).build();
    }
    @GetMapping("/jugadors/edad/{id}")
     @Timed
     public ResponseEntity<List<JugadorDTO>> getAllJugadorsByCategoria(@PathVariable int id)
         throws URISyntaxException {
                log.debug("REST request to get a list of Jugadors by edad");
                List<JugadorDTO> players = jugadorService.findByEdad(id);
                return new ResponseEntity<>(players,HttpStatus.OK);
    }
}
