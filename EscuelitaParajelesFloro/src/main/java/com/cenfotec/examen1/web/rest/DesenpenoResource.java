package com.cenfotec.examen1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.examen1.service.DesenpenoService;
import com.cenfotec.examen1.web.rest.util.HeaderUtil;
import com.cenfotec.examen1.web.rest.util.PaginationUtil;
import com.cenfotec.examen1.service.dto.DesenpenoDTO;

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
 * REST controller for managing Desenpeno.
 */
@RestController
@RequestMapping("/api")
public class DesenpenoResource {

    private final Logger log = LoggerFactory.getLogger(DesenpenoResource.class);
        
    @Inject
    private DesenpenoService desenpenoService;

    /**
     * POST  /desenpenos : Create a new desenpeno.
     *
     * @param desenpenoDTO the desenpenoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new desenpenoDTO, or with status 400 (Bad Request) if the desenpeno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/desenpenos")
    @Timed
    public ResponseEntity<DesenpenoDTO> createDesenpeno(@Valid @RequestBody DesenpenoDTO desenpenoDTO) throws URISyntaxException {
        log.debug("REST request to save Desenpeno : {}", desenpenoDTO);
        if (desenpenoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("desenpeno", "idexists", "A new desenpeno cannot already have an ID")).body(null);
        }
        DesenpenoDTO result = desenpenoService.save(desenpenoDTO);
        return ResponseEntity.created(new URI("/api/desenpenos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("desenpeno", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /desenpenos : Updates an existing desenpeno.
     *
     * @param desenpenoDTO the desenpenoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated desenpenoDTO,
     * or with status 400 (Bad Request) if the desenpenoDTO is not valid,
     * or with status 500 (Internal Server Error) if the desenpenoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/desenpenos")
    @Timed
    public ResponseEntity<DesenpenoDTO> updateDesenpeno(@Valid @RequestBody DesenpenoDTO desenpenoDTO) throws URISyntaxException {
        log.debug("REST request to update Desenpeno : {}", desenpenoDTO);
        if (desenpenoDTO.getId() == null) {
            return createDesenpeno(desenpenoDTO);
        }
        DesenpenoDTO result = desenpenoService.save(desenpenoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("desenpeno", desenpenoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /desenpenos : get all the desenpenos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of desenpenos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/desenpenos")
    @Timed
    public ResponseEntity<List<DesenpenoDTO>> getAllDesenpenos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Desenpenos");
        Page<DesenpenoDTO> page = desenpenoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/desenpenos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /desenpenos/:id : get the "id" desenpeno.
     *
     * @param id the id of the desenpenoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the desenpenoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/desenpenos/{id}")
    @Timed
    public ResponseEntity<DesenpenoDTO> getDesenpeno(@PathVariable Long id) {
        log.debug("REST request to get Desenpeno : {}", id);
        DesenpenoDTO desenpenoDTO = desenpenoService.findOne(id);
        return Optional.ofNullable(desenpenoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /desenpenos/:id : delete the "id" desenpeno.
     *
     * @param id the id of the desenpenoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/desenpenos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesenpeno(@PathVariable Long id) {
        log.debug("REST request to delete Desenpeno : {}", id);
        desenpenoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("desenpeno", id.toString())).build();
    }

}
