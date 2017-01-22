package com.cenfotec.examen1.web.rest;

import com.cenfotec.examen1.EscuelitaParajelesFloroApp;

import com.cenfotec.examen1.domain.Desenpeno;
import com.cenfotec.examen1.repository.DesenpenoRepository;
import com.cenfotec.examen1.service.DesenpenoService;
import com.cenfotec.examen1.service.dto.DesenpenoDTO;
import com.cenfotec.examen1.service.mapper.DesenpenoMapper;

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
 * Test class for the DesenpenoResource REST controller.
 *
 * @see DesenpenoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaParajelesFloroApp.class)
public class DesenpenoResourceIntTest {

    private static final Integer DEFAULT_CALIFICACION = 0;
    private static final Integer UPDATED_CALIFICACION = 1;

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    @Inject
    private DesenpenoRepository desenpenoRepository;

    @Inject
    private DesenpenoMapper desenpenoMapper;

    @Inject
    private DesenpenoService desenpenoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDesenpenoMockMvc;

    private Desenpeno desenpeno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DesenpenoResource desenpenoResource = new DesenpenoResource();
        ReflectionTestUtils.setField(desenpenoResource, "desenpenoService", desenpenoService);
        this.restDesenpenoMockMvc = MockMvcBuilders.standaloneSetup(desenpenoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Desenpeno createEntity(EntityManager em) {
        Desenpeno desenpeno = new Desenpeno()
                .calificacion(DEFAULT_CALIFICACION)
                .notas(DEFAULT_NOTAS);
        return desenpeno;
    }

    @Before
    public void initTest() {
        desenpeno = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesenpeno() throws Exception {
        int databaseSizeBeforeCreate = desenpenoRepository.findAll().size();

        // Create the Desenpeno
        DesenpenoDTO desenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno);

        restDesenpenoMockMvc.perform(post("/api/desenpenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desenpenoDTO)))
            .andExpect(status().isCreated());

        // Validate the Desenpeno in the database
        List<Desenpeno> desenpenoList = desenpenoRepository.findAll();
        assertThat(desenpenoList).hasSize(databaseSizeBeforeCreate + 1);
        Desenpeno testDesenpeno = desenpenoList.get(desenpenoList.size() - 1);
        assertThat(testDesenpeno.getCalificacion()).isEqualTo(DEFAULT_CALIFICACION);
        assertThat(testDesenpeno.getNotas()).isEqualTo(DEFAULT_NOTAS);
    }

    @Test
    @Transactional
    public void createDesenpenoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = desenpenoRepository.findAll().size();

        // Create the Desenpeno with an existing ID
        Desenpeno existingDesenpeno = new Desenpeno();
        existingDesenpeno.setId(1L);
        DesenpenoDTO existingDesenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(existingDesenpeno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesenpenoMockMvc.perform(post("/api/desenpenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDesenpenoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Desenpeno> desenpenoList = desenpenoRepository.findAll();
        assertThat(desenpenoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCalificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = desenpenoRepository.findAll().size();
        // set the field null
        desenpeno.setCalificacion(null);

        // Create the Desenpeno, which fails.
        DesenpenoDTO desenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno);

        restDesenpenoMockMvc.perform(post("/api/desenpenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desenpenoDTO)))
            .andExpect(status().isBadRequest());

        List<Desenpeno> desenpenoList = desenpenoRepository.findAll();
        assertThat(desenpenoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesenpenos() throws Exception {
        // Initialize the database
        desenpenoRepository.saveAndFlush(desenpeno);

        // Get all the desenpenoList
        restDesenpenoMockMvc.perform(get("/api/desenpenos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(desenpeno.getId().intValue())))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void getDesenpeno() throws Exception {
        // Initialize the database
        desenpenoRepository.saveAndFlush(desenpeno);

        // Get the desenpeno
        restDesenpenoMockMvc.perform(get("/api/desenpenos/{id}", desenpeno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(desenpeno.getId().intValue()))
            .andExpect(jsonPath("$.calificacion").value(DEFAULT_CALIFICACION))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesenpeno() throws Exception {
        // Get the desenpeno
        restDesenpenoMockMvc.perform(get("/api/desenpenos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesenpeno() throws Exception {
        // Initialize the database
        desenpenoRepository.saveAndFlush(desenpeno);
        int databaseSizeBeforeUpdate = desenpenoRepository.findAll().size();

        // Update the desenpeno
        Desenpeno updatedDesenpeno = desenpenoRepository.findOne(desenpeno.getId());
        updatedDesenpeno
                .calificacion(UPDATED_CALIFICACION)
                .notas(UPDATED_NOTAS);
        DesenpenoDTO desenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(updatedDesenpeno);

        restDesenpenoMockMvc.perform(put("/api/desenpenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desenpenoDTO)))
            .andExpect(status().isOk());

        // Validate the Desenpeno in the database
        List<Desenpeno> desenpenoList = desenpenoRepository.findAll();
        assertThat(desenpenoList).hasSize(databaseSizeBeforeUpdate);
        Desenpeno testDesenpeno = desenpenoList.get(desenpenoList.size() - 1);
        assertThat(testDesenpeno.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
        assertThat(testDesenpeno.getNotas()).isEqualTo(UPDATED_NOTAS);
    }

    @Test
    @Transactional
    public void updateNonExistingDesenpeno() throws Exception {
        int databaseSizeBeforeUpdate = desenpenoRepository.findAll().size();

        // Create the Desenpeno
        DesenpenoDTO desenpenoDTO = desenpenoMapper.desenpenoToDesenpenoDTO(desenpeno);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesenpenoMockMvc.perform(put("/api/desenpenos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(desenpenoDTO)))
            .andExpect(status().isCreated());

        // Validate the Desenpeno in the database
        List<Desenpeno> desenpenoList = desenpenoRepository.findAll();
        assertThat(desenpenoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesenpeno() throws Exception {
        // Initialize the database
        desenpenoRepository.saveAndFlush(desenpeno);
        int databaseSizeBeforeDelete = desenpenoRepository.findAll().size();

        // Get the desenpeno
        restDesenpenoMockMvc.perform(delete("/api/desenpenos/{id}", desenpeno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Desenpeno> desenpenoList = desenpenoRepository.findAll();
        assertThat(desenpenoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
