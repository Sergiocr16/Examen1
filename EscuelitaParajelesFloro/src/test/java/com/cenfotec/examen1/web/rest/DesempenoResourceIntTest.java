package com.cenfotec.examen1.web.rest;

import com.cenfotec.examen1.EscuelitaParajelesFloroApp;

import com.cenfotec.examen1.domain.Desempeno;
import com.cenfotec.examen1.repository.DesempenoRepository;
import com.cenfotec.examen1.service.DesempenoService;
import com.cenfotec.examen1.service.dto.DesempenoDTO;
import com.cenfotec.examen1.service.mapper.DesempenoMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DesempenoResource REST controller.
 *
 * @see DesempenoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaParajelesFloroApp.class)
public class DesempenoResourceIntTest {

    private static final Integer DEFAULT_CALIFICACION = 0;
    private static final Integer UPDATED_CALIFICACION = 1;

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    @Inject
    private DesempenoRepository desempenoRepository;

    @Inject
    private DesempenoMapper desempenoMapper;

    @Inject
    private DesempenoService desempenoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDesempenoMockMvc;

    private Desempeno desempeno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DesempenoResource desempenoResource = new DesempenoResource();
        ReflectionTestUtils.setField(desempenoResource, "desempenoService", desempenoService);
        this.restDesempenoMockMvc = MockMvcBuilders.standaloneSetup(desempenoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Desempeno createEntity(EntityManager em) {
        Desempeno desempeno = new Desempeno()
                .calificacion(DEFAULT_CALIFICACION)
                .notas(DEFAULT_NOTAS);
        return desempeno;
    }

    @Before
    public void initTest() {
        desempeno = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesempeno() throws Exception {
        int databaseSizeBeforeCreate = desempenoRepository.findAll().size();

        // Create the Desempeno
        DesempenoDTO desempenoDTO = desempenoMapper.desempenoToDesempenoDTO(desempeno);

        restDesempenoMockMvc.perform(post("/api/desempenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desempenoDTO)))
            .andExpect(status().isCreated());

        // Validate the Desempeno in the database
        List<Desempeno> desempenoList = desempenoRepository.findAll();
        assertThat(desempenoList).hasSize(databaseSizeBeforeCreate + 1);
        Desempeno testDesempeno = desempenoList.get(desempenoList.size() - 1);
        assertThat(testDesempeno.getCalificacion()).isEqualTo(DEFAULT_CALIFICACION);
        assertThat(testDesempeno.getNotas()).isEqualTo(DEFAULT_NOTAS);
    }

    @Test
    @Transactional
    public void createDesempenoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = desempenoRepository.findAll().size();

        // Create the Desempeno with an existing ID
        Desempeno existingDesempeno = new Desempeno();
        existingDesempeno.setId(1L);
        DesempenoDTO existingDesempenoDTO = desempenoMapper.desempenoToDesempenoDTO(existingDesempeno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesempenoMockMvc.perform(post("/api/desempenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDesempenoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Desempeno> desempenoList = desempenoRepository.findAll();
        assertThat(desempenoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCalificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = desempenoRepository.findAll().size();
        // set the field null
        desempeno.setCalificacion(null);

        // Create the Desempeno, which fails.
        DesempenoDTO desempenoDTO = desempenoMapper.desempenoToDesempenoDTO(desempeno);

        restDesempenoMockMvc.perform(post("/api/desempenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desempenoDTO)))
            .andExpect(status().isBadRequest());

        List<Desempeno> desempenoList = desempenoRepository.findAll();
        assertThat(desempenoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesempenos() throws Exception {
        // Initialize the database
        desempenoRepository.saveAndFlush(desempeno);

        // Get all the desempenoList
        restDesempenoMockMvc.perform(get("/api/desempenos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(desempeno.getId().intValue())))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void getDesempeno() throws Exception {
        // Initialize the database
        desempenoRepository.saveAndFlush(desempeno);

        // Get the desempeno
        restDesempenoMockMvc.perform(get("/api/desempenos/{id}", desempeno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(desempeno.getId().intValue()))
            .andExpect(jsonPath("$.calificacion").value(DEFAULT_CALIFICACION))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesempeno() throws Exception {
        // Get the desempeno
        restDesempenoMockMvc.perform(get("/api/desempenos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesempeno() throws Exception {
        // Initialize the database
        desempenoRepository.saveAndFlush(desempeno);
        int databaseSizeBeforeUpdate = desempenoRepository.findAll().size();

        // Update the desempeno
        Desempeno updatedDesempeno = desempenoRepository.findOne(desempeno.getId());
        updatedDesempeno
                .calificacion(UPDATED_CALIFICACION)
                .notas(UPDATED_NOTAS);
        DesempenoDTO desempenoDTO = desempenoMapper.desempenoToDesempenoDTO(updatedDesempeno);

        restDesempenoMockMvc.perform(put("/api/desempenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desempenoDTO)))
            .andExpect(status().isOk());

        // Validate the Desempeno in the database
        List<Desempeno> desempenoList = desempenoRepository.findAll();
        assertThat(desempenoList).hasSize(databaseSizeBeforeUpdate);
        Desempeno testDesempeno = desempenoList.get(desempenoList.size() - 1);
        assertThat(testDesempeno.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
        assertThat(testDesempeno.getNotas()).isEqualTo(UPDATED_NOTAS);
    }

    @Test
    @Transactional
    public void updateNonExistingDesempeno() throws Exception {
        int databaseSizeBeforeUpdate = desempenoRepository.findAll().size();

        // Create the Desempeno
        DesempenoDTO desempenoDTO = desempenoMapper.desempenoToDesempenoDTO(desempeno);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesempenoMockMvc.perform(put("/api/desempenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desempenoDTO)))
            .andExpect(status().isCreated());

        // Validate the Desempeno in the database
        List<Desempeno> desempenoList = desempenoRepository.findAll();
        assertThat(desempenoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesempeno() throws Exception {
        // Initialize the database
        desempenoRepository.saveAndFlush(desempeno);
        int databaseSizeBeforeDelete = desempenoRepository.findAll().size();

        // Get the desempeno
        restDesempenoMockMvc.perform(delete("/api/desempenos/{id}", desempeno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Desempeno> desempenoList = desempenoRepository.findAll();
        assertThat(desempenoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
