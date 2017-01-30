package com.cenfotec.examen1.web.rest;

import com.cenfotec.examen1.EscuelitaParajelesFloroApp;

import com.cenfotec.examen1.domain.Jugador;
import com.cenfotec.examen1.repository.JugadorRepository;
import com.cenfotec.examen1.service.JugadorService;
import com.cenfotec.examen1.service.dto.JugadorDTO;
import com.cenfotec.examen1.service.mapper.JugadorMapper;

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
 * Test class for the JugadorResource REST controller.
 *
 * @see JugadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaParajelesFloroApp.class)
public class JugadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD = 0;
    private static final Integer UPDATED_EDAD = 1;

    private static final String DEFAULT_CEDULA = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA = "BBBBBBBBBB";

    private static final String DEFAULT_POSICION = "AAAAAAAAAA";
    private static final String UPDATED_POSICION = "BBBBBBBBBB";

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private JugadorMapper jugadorMapper;

    @Inject
    private JugadorService jugadorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JugadorResource jugadorResource = new JugadorResource();
        ReflectionTestUtils.setField(jugadorResource, "jugadorService", jugadorService);
        this.restJugadorMockMvc = MockMvcBuilders.standaloneSetup(jugadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jugador createEntity(EntityManager em) {
        Jugador jugador = new Jugador()
                .nombre(DEFAULT_NOMBRE)
                .apellido(DEFAULT_APELLIDO)
                .edad(DEFAULT_EDAD)
                .cedula(DEFAULT_CEDULA)
                .posicion(DEFAULT_POSICION);
        return jugador;
    }

    @Before
    public void initTest() {
        jugador = createEntity(em);
    }

    @Test
    @Transactional
    public void createJugador() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeCreate + 1);
        Jugador testJugador = jugadorList.get(jugadorList.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJugador.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testJugador.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testJugador.getCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testJugador.getPosicion()).isEqualTo(DEFAULT_POSICION);
    }

    @Test
    @Transactional
    public void createJugadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador with an existing ID
        Jugador existingJugador = new Jugador();
        existingJugador.setId(1L);
        JugadorDTO existingJugadorDTO = jugadorMapper.jugadorToJugadorDTO(existingJugador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJugadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setNombre(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setApellido(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEdadIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setEdad(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCedulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setCedula(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJugadors() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadorList
        restJugadorMockMvc.perform(get("/api/jugadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA.toString())))
            .andExpect(jsonPath("$.[*].posicion").value(hasItem(DEFAULT_POSICION.toString())));
    }

    @Test
    @Transactional
    public void getJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA.toString()))
            .andExpect(jsonPath("$.posicion").value(DEFAULT_POSICION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);
        int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Update the jugador
        Jugador updatedJugador = jugadorRepository.findOne(jugador.getId());
        updatedJugador
                .nombre(UPDATED_NOMBRE)
                .apellido(UPDATED_APELLIDO)
                .edad(UPDATED_EDAD)
                .cedula(UPDATED_CEDULA)
                .posicion(UPDATED_POSICION);
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(updatedJugador);

        restJugadorMockMvc.perform(put("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isOk());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeUpdate);
        Jugador testJugador = jugadorList.get(jugadorList.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJugador.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testJugador.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testJugador.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testJugador.getPosicion()).isEqualTo(UPDATED_POSICION);
    }

    @Test
    @Transactional
    public void updateNonExistingJugador() throws Exception {
        int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJugadorMockMvc.perform(put("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);
        int databaseSizeBeforeDelete = jugadorRepository.findAll().size();

        // Get the jugador
        restJugadorMockMvc.perform(delete("/api/jugadors/{id}", jugador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
