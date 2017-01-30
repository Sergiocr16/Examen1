package com.cenfotec.examen1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.examen1.service.EntrenamientoService;
import com.cenfotec.examen1.web.rest.util.HeaderUtil;
import com.cenfotec.examen1.web.rest.util.PaginationUtil;
import com.cenfotec.examen1.service.dto.EntrenamientoDTO;

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
 * REST controller for managing Entrenamiento.
 */
@RestController
@RequestMapping("/api")
public class EntrenamientoResource {

    private final Logger log = LoggerFactory.getLogger(EntrenamientoResource.class);
        
    @Inject
    private EntrenamientoService entrenamientoService;

    /**
     * POST  /entrenamientos : Create a new entrenamiento.
     *
     * @param entrenamientoDTO the entrenamientoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrenamientoDTO, or with status 400 (Bad Request) if the entrenamiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entrenamientos")
    @Timed
    public ResponseEntity<EntrenamientoDTO> createEntrenamiento(@Valid @RequestBody EntrenamientoDTO entrenamientoDTO) throws URISyntaxException {
        log.debug("REST request to save Entrenamiento : {}", entrenamientoDTO);
        if (entrenamientoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entrenamiento", "idexists", "A new entrenamiento cannot already have an ID")).body(null);
        }
        EntrenamientoDTO result = entrenamientoService.save(entrenamientoDTO);
        return ResponseEntity.created(new URI("/api/entrenamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entrenamiento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entrenamientos : Updates an existing entrenamiento.
     *
     * @param entrenamientoDTO the entrenamientoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrenamientoDTO,
     * or with status 400 (Bad Request) if the entrenamientoDTO is not valid,
     * or with status 500 (Internal Server Error) if the entrenamientoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entrenamientos")
    @Timed
    public ResponseEntity<EntrenamientoDTO> updateEntrenamiento(@Valid @RequestBody EntrenamientoDTO entrenamientoDTO) throws URISyntaxException {
        log.debug("REST request to update Entrenamiento : {}", entrenamientoDTO);
        if (entrenamientoDTO.getId() == null) {
            return createEntrenamiento(entrenamientoDTO);
        }
        EntrenamientoDTO result = entrenamientoService.save(entrenamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entrenamiento", entrenamientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entrenamientos : get all the entrenamientos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entrenamientos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/entrenamientos")
    @Timed
    public ResponseEntity<List<EntrenamientoDTO>> getAllEntrenamientos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Entrenamientos");
        Page<EntrenamientoDTO> page = entrenamientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entrenamientos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entrenamientos/:id : get the "id" entrenamiento.
     *
     * @param id the id of the entrenamientoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrenamientoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entrenamientos/{id}")
    @Timed
    public ResponseEntity<EntrenamientoDTO> getEntrenamiento(@PathVariable Long id) {
        log.debug("REST request to get Entrenamiento : {}", id);
        EntrenamientoDTO entrenamientoDTO = entrenamientoService.findOne(id);
        return Optional.ofNullable(entrenamientoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entrenamientos/:id : delete the "id" entrenamiento.
     *
     * @param id the id of the entrenamientoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entrenamientos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrenamiento(@PathVariable Long id) {
        log.debug("REST request to delete Entrenamiento : {}", id);
        entrenamientoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entrenamiento", id.toString())).build();
    }

}
