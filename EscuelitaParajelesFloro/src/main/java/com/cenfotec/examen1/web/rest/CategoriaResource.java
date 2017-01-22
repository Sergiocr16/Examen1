package com.cenfotec.examen1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.examen1.service.CategoriaService;
import com.cenfotec.examen1.web.rest.util.HeaderUtil;
import com.cenfotec.examen1.web.rest.util.PaginationUtil;
import com.cenfotec.examen1.service.dto.CategoriaDTO;

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
 * REST controller for managing Categoria.
 */
@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);
        
    @Inject
    private CategoriaService categoriaService;

    /**
     * POST  /categorias : Create a new categoria.
     *
     * @param categoriaDTO the categoriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoriaDTO, or with status 400 (Bad Request) if the categoria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorias")
    @Timed
    public ResponseEntity<CategoriaDTO> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("categoria", "idexists", "A new categoria cannot already have an ID")).body(null);
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("categoria", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorias : Updates an existing categoria.
     *
     * @param categoriaDTO the categoriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoriaDTO,
     * or with status 400 (Bad Request) if the categoriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoriaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorias")
    @Timed
    public ResponseEntity<CategoriaDTO> updateCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() == null) {
            return createCategoria(categoriaDTO);
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("categoria", categoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorias : get all the categorias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categorias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/categorias")
    @Timed
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Categorias");
        Page<CategoriaDTO> page = categoriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categorias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /categorias/:id : get the "id" categoria.
     *
     * @param id the id of the categoriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categorias/{id}")
    @Timed
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        CategoriaDTO categoriaDTO = categoriaService.findOne(id);
        return Optional.ofNullable(categoriaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /categorias/:id : delete the "id" categoria.
     *
     * @param id the id of the categoriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorias/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        categoriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("categoria", id.toString())).build();
    }

}
