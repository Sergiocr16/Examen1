package com.cenfotec.examen1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.examen1.service.DesempenoService;
import com.cenfotec.examen1.web.rest.util.HeaderUtil;
import com.cenfotec.examen1.web.rest.util.PaginationUtil;
import com.cenfotec.examen1.service.dto.DesempenoDTO;

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
 * REST controller for managing Desempeno.
 */
@RestController
@RequestMapping("/api")
public class DesempenoResource {

    private final Logger log = LoggerFactory.getLogger(DesempenoResource.class);

    @Inject
    private DesempenoService desempenoService;

    /**
     * POST  /desempenos : Create a new desempeno.
     *
     * @param desempenoDTO the desempenoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new desempenoDTO, or with status 400 (Bad Request) if the desempeno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/desempenos")
    @Timed
    public ResponseEntity<DesempenoDTO> createDesempeno(@Valid @RequestBody DesempenoDTO desempenoDTO) throws URISyntaxException {
        log.debug("REST request to save Desempeno : {}", desempenoDTO);
        if (desempenoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("desempeno", "idexists", "A new desempeno cannot already have an ID")).body(null);
        }
        DesempenoDTO result = desempenoService.save(desempenoDTO);
        return ResponseEntity.created(new URI("/api/desempenos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("desempeno", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /desempenos : Updates an existing desempeno.
     *
     * @param desempenoDTO the desempenoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated desempenoDTO,
     * or with status 400 (Bad Request) if the desempenoDTO is not valid,
     * or with status 500 (Internal Server Error) if the desempenoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/desempenos")
    @Timed
    public ResponseEntity<DesempenoDTO> updateDesempeno(@Valid @RequestBody DesempenoDTO desempenoDTO) throws URISyntaxException {
        log.debug("REST request to update Desempeno : {}", desempenoDTO);
        if (desempenoDTO.getId() == null) {
            return createDesempeno(desempenoDTO);
        }
        DesempenoDTO result = desempenoService.save(desempenoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("desempeno", desempenoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /desempenos : get all the desempenos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of desempenos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/desempenos")
    @Timed
    public ResponseEntity<List<DesempenoDTO>> getAllDesempenos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Desempenos");
        Page<DesempenoDTO> page = desempenoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/desempenos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /desempenos/:id : get the "id" desempeno.
     *
     * @param id the id of the desempenoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the desempenoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/desempenos/{id}")
    @Timed
    public ResponseEntity<DesempenoDTO> getDesempeno(@PathVariable Long id) {
        log.debug("REST request to get Desempeno : {}", id);
        DesempenoDTO desempenoDTO = desempenoService.findOne(id);
        return Optional.ofNullable(desempenoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /desempenos/:id : delete the "id" desempeno.
     *
     * @param id the id of the desempenoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/desempenos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesempeno(@PathVariable Long id) {
        log.debug("REST request to delete Desempeno : {}", id);
        desempenoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("desempeno", id.toString())).build();
    }

    @GetMapping("/desenpenosFindByEntrenamientoIdAndJugadorId/{entrenamientoId}/{jugadorId}")
     @Timed
     public ResponseEntity<DesempenoDTO> getDesenpenoByEntrenamientoAndJugador(
         @PathVariable(value = "entrenamientoId") Long entrenamientoId,
         @PathVariable(value = "jugadorId")  Long jugadorId) {
        //        log.debug("REST request to get Desenpeno : {}", id);
        DesempenoDTO desenpenoDTO = desempenoService.findByEntrenamientoIdAndJugadorId(entrenamientoId,jugadorId);
                return Optional.ofNullable(desenpenoDTO)
                    .map(result -> new ResponseEntity<>(
                            result,
                            HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }

}
