package com.cenfotec.examen1.web.rest;

import com.cenfotec.examen1.EscuelitaParajelesFloroApp;

import com.cenfotec.examen1.domain.Entrenamiento;
import com.cenfotec.examen1.repository.EntrenamientoRepository;
import com.cenfotec.examen1.service.EntrenamientoService;
import com.cenfotec.examen1.service.dto.EntrenamientoDTO;
import com.cenfotec.examen1.service.mapper.EntrenamientoMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntrenamientoResource REST controller.
 *
 * @see EntrenamientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaParajelesFloroApp.class)
public class EntrenamientoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_HORA_INICIO = 0;
    private static final Integer UPDATED_HORA_INICIO = 1;

    private static final Integer DEFAULT_HORA_FIN = 0;
    private static final Integer UPDATED_HORA_FIN = 1;

    private static final Integer DEFAULT_CATEGORIA = 1;
    private static final Integer UPDATED_CATEGORIA = 2;

    @Inject
    private EntrenamientoRepository entrenamientoRepository;

    @Inject
    private EntrenamientoMapper entrenamientoMapper;

    @Inject
    private EntrenamientoService entrenamientoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrenamientoMockMvc;

    private Entrenamiento entrenamiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrenamientoResource entrenamientoResource = new EntrenamientoResource();
        ReflectionTestUtils.setField(entrenamientoResource, "entrenamientoService", entrenamientoService);
        this.restEntrenamientoMockMvc = MockMvcBuilders.standaloneSetup(entrenamientoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrenamiento createEntity(EntityManager em) {
        Entrenamiento entrenamiento = new Entrenamiento()
                .descripcion(DEFAULT_DESCRIPCION)
                .fecha(DEFAULT_FECHA)
                .horaInicio(DEFAULT_HORA_INICIO)
                .horaFin(DEFAULT_HORA_FIN)
                .categoria(DEFAULT_CATEGORIA);
        return entrenamiento;
    }

    @Before
    public void initTest() {
        entrenamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrenamiento() throws Exception {
        int databaseSizeBeforeCreate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Entrenamiento testEntrenamiento = entrenamientoList.get(entrenamientoList.size() - 1);
        assertThat(testEntrenamiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEntrenamiento.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEntrenamiento.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testEntrenamiento.getHoraFin()).isEqualTo(DEFAULT_HORA_FIN);
        assertThat(testEntrenamiento.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    public void createEntrenamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento with an existing ID
        Entrenamiento existingEntrenamiento = new Entrenamiento();
        existingEntrenamiento.setId(1L);
        EntrenamientoDTO existingEntrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(existingEntrenamiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEntrenamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenamientoRepository.findAll().size();
        // set the field null
        entrenamiento.setDescripcion(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenamientoRepository.findAll().size();
        // set the field null
        entrenamiento.setFecha(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenamientoRepository.findAll().size();
        // set the field null
        entrenamiento.setHoraInicio(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenamientoRepository.findAll().size();
        // set the field null
        entrenamiento.setHoraFin(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenamientoRepository.findAll().size();
        // set the field null
        entrenamiento.setCategoria(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntrenamientos() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);

        // Get all the entrenamientoList
        restEntrenamientoMockMvc.perform(get("/api/entrenamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrenamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO)))
            .andExpect(jsonPath("$.[*].horaFin").value(hasItem(DEFAULT_HORA_FIN)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));
    }

    @Test
    @Transactional
    public void getEntrenamiento() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);

        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(get("/api/entrenamientos/{id}", entrenamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrenamiento.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO))
            .andExpect(jsonPath("$.horaFin").value(DEFAULT_HORA_FIN))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA));
    }

    @Test
    @Transactional
    public void getNonExistingEntrenamiento() throws Exception {
        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(get("/api/entrenamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrenamiento() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);
        int databaseSizeBeforeUpdate = entrenamientoRepository.findAll().size();

        // Update the entrenamiento
        Entrenamiento updatedEntrenamiento = entrenamientoRepository.findOne(entrenamiento.getId());
        updatedEntrenamiento
                .descripcion(UPDATED_DESCRIPCION)
                .fecha(UPDATED_FECHA)
                .horaInicio(UPDATED_HORA_INICIO)
                .horaFin(UPDATED_HORA_FIN)
                .categoria(UPDATED_CATEGORIA);
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(updatedEntrenamiento);

        restEntrenamientoMockMvc.perform(put("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isOk());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeUpdate);
        Entrenamiento testEntrenamiento = entrenamientoList.get(entrenamientoList.size() - 1);
        assertThat(testEntrenamiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEntrenamiento.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEntrenamiento.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testEntrenamiento.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
        assertThat(testEntrenamiento.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrenamiento() throws Exception {
        int databaseSizeBeforeUpdate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntrenamientoMockMvc.perform(put("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntrenamiento() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);
        int databaseSizeBeforeDelete = entrenamientoRepository.findAll().size();

        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(delete("/api/entrenamientos/{id}", entrenamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
