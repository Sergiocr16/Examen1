package com.cenfotec.examen1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.examen1.service.ResponsableService;
import com.cenfotec.examen1.web.rest.util.HeaderUtil;
import com.cenfotec.examen1.web.rest.util.PaginationUtil;
import com.cenfotec.examen1.service.dto.ResponsableDTO;

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
 * REST controller for managing Responsable.
 */
@RestController
@RequestMapping("/api")
public class ResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableResource.class);
        
    @Inject
    private ResponsableService responsableService;

    /**
     * POST  /responsables : Create a new responsable.
     *
     * @param responsableDTO the responsableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responsableDTO, or with status 400 (Bad Request) if the responsable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/responsables")
    @Timed
    public ResponseEntity<ResponsableDTO> createResponsable(@Valid @RequestBody ResponsableDTO responsableDTO) throws URISyntaxException {
        log.debug("REST request to save Responsable : {}", responsableDTO);
        if (responsableDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("responsable", "idexists", "A new responsable cannot already have an ID")).body(null);
        }
        ResponsableDTO result = responsableService.save(responsableDTO);
        return ResponseEntity.created(new URI("/api/responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("responsable", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsables : Updates an existing responsable.
     *
     * @param responsableDTO the responsableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responsableDTO,
     * or with status 400 (Bad Request) if the responsableDTO is not valid,
     * or with status 500 (Internal Server Error) if the responsableDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/responsables")
    @Timed
    public ResponseEntity<ResponsableDTO> updateResponsable(@Valid @RequestBody ResponsableDTO responsableDTO) throws URISyntaxException {
        log.debug("REST request to update Responsable : {}", responsableDTO);
        if (responsableDTO.getId() == null) {
            return createResponsable(responsableDTO);
        }
        ResponsableDTO result = responsableService.save(responsableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("responsable", responsableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsables : get all the responsables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of responsables in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/responsables")
    @Timed
    public ResponseEntity<List<ResponsableDTO>> getAllResponsables(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Responsables");
        Page<ResponsableDTO> page = responsableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /responsables/:id : get the "id" responsable.
     *
     * @param id the id of the responsableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responsableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/responsables/{id}")
    @Timed
    public ResponseEntity<ResponsableDTO> getResponsable(@PathVariable Long id) {
        log.debug("REST request to get Responsable : {}", id);
        ResponsableDTO responsableDTO = responsableService.findOne(id);
        return Optional.ofNullable(responsableDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /responsables/:id : delete the "id" responsable.
     *
     * @param id the id of the responsableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/responsables/{id}")
    @Timed
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        log.debug("REST request to delete Responsable : {}", id);
        responsableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("responsable", id.toString())).build();
    }

}
