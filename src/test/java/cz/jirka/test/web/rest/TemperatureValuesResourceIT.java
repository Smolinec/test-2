package cz.jirka.test.web.rest;

import cz.jirka.test.TestMemoryH2App;
import cz.jirka.test.domain.TemperatureValues;
import cz.jirka.test.repository.TemperatureValuesRepository;
import cz.jirka.test.service.TemperatureValuesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static cz.jirka.test.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TemperatureValuesResource} REST controller.
 */
@SpringBootTest(classes = TestMemoryH2App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TemperatureValuesResourceIT {

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TemperatureValuesRepository temperatureValuesRepository;

    @Mock
    private TemperatureValuesRepository temperatureValuesRepositoryMock;

    @Mock
    private TemperatureValuesService temperatureValuesServiceMock;

    @Autowired
    private TemperatureValuesService temperatureValuesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemperatureValuesMockMvc;

    private TemperatureValues temperatureValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemperatureValues createEntity(EntityManager em) {
        TemperatureValues temperatureValues = new TemperatureValues()
            .value(DEFAULT_VALUE)
            .timestamp(DEFAULT_TIMESTAMP);
        return temperatureValues;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemperatureValues createUpdatedEntity(EntityManager em) {
        TemperatureValues temperatureValues = new TemperatureValues()
            .value(UPDATED_VALUE)
            .timestamp(UPDATED_TIMESTAMP);
        return temperatureValues;
    }

    @BeforeEach
    public void initTest() {
        temperatureValues = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemperatureValues() throws Exception {
        int databaseSizeBeforeCreate = temperatureValuesRepository.findAll().size();
        // Create the TemperatureValues
        restTemperatureValuesMockMvc.perform(post("/api/temperature-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(temperatureValues)))
            .andExpect(status().isCreated());

        // Validate the TemperatureValues in the database
        List<TemperatureValues> temperatureValuesList = temperatureValuesRepository.findAll();
        assertThat(temperatureValuesList).hasSize(databaseSizeBeforeCreate + 1);
        TemperatureValues testTemperatureValues = temperatureValuesList.get(temperatureValuesList.size() - 1);
        assertThat(testTemperatureValues.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTemperatureValues.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createTemperatureValuesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temperatureValuesRepository.findAll().size();

        // Create the TemperatureValues with an existing ID
        temperatureValues.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemperatureValuesMockMvc.perform(post("/api/temperature-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(temperatureValues)))
            .andExpect(status().isBadRequest());

        // Validate the TemperatureValues in the database
        List<TemperatureValues> temperatureValuesList = temperatureValuesRepository.findAll();
        assertThat(temperatureValuesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTemperatureValues() throws Exception {
        // Initialize the database
        temperatureValuesRepository.saveAndFlush(temperatureValues);

        // Get all the temperatureValuesList
        restTemperatureValuesMockMvc.perform(get("/api/temperature-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temperatureValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTemperatureValuesWithEagerRelationshipsIsEnabled() throws Exception {
        when(temperatureValuesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemperatureValuesMockMvc.perform(get("/api/temperature-values?eagerload=true"))
            .andExpect(status().isOk());

        verify(temperatureValuesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTemperatureValuesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(temperatureValuesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemperatureValuesMockMvc.perform(get("/api/temperature-values?eagerload=true"))
            .andExpect(status().isOk());

        verify(temperatureValuesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTemperatureValues() throws Exception {
        // Initialize the database
        temperatureValuesRepository.saveAndFlush(temperatureValues);

        // Get the temperatureValues
        restTemperatureValuesMockMvc.perform(get("/api/temperature-values/{id}", temperatureValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(temperatureValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }
    @Test
    @Transactional
    public void getNonExistingTemperatureValues() throws Exception {
        // Get the temperatureValues
        restTemperatureValuesMockMvc.perform(get("/api/temperature-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemperatureValues() throws Exception {
        // Initialize the database
        temperatureValuesService.save(temperatureValues);

        int databaseSizeBeforeUpdate = temperatureValuesRepository.findAll().size();

        // Update the temperatureValues
        TemperatureValues updatedTemperatureValues = temperatureValuesRepository.findById(temperatureValues.getId()).get();
        // Disconnect from session so that the updates on updatedTemperatureValues are not directly saved in db
        em.detach(updatedTemperatureValues);
        updatedTemperatureValues
            .value(UPDATED_VALUE)
            .timestamp(UPDATED_TIMESTAMP);

        restTemperatureValuesMockMvc.perform(put("/api/temperature-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemperatureValues)))
            .andExpect(status().isOk());

        // Validate the TemperatureValues in the database
        List<TemperatureValues> temperatureValuesList = temperatureValuesRepository.findAll();
        assertThat(temperatureValuesList).hasSize(databaseSizeBeforeUpdate);
        TemperatureValues testTemperatureValues = temperatureValuesList.get(temperatureValuesList.size() - 1);
        assertThat(testTemperatureValues.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTemperatureValues.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingTemperatureValues() throws Exception {
        int databaseSizeBeforeUpdate = temperatureValuesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemperatureValuesMockMvc.perform(put("/api/temperature-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(temperatureValues)))
            .andExpect(status().isBadRequest());

        // Validate the TemperatureValues in the database
        List<TemperatureValues> temperatureValuesList = temperatureValuesRepository.findAll();
        assertThat(temperatureValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemperatureValues() throws Exception {
        // Initialize the database
        temperatureValuesService.save(temperatureValues);

        int databaseSizeBeforeDelete = temperatureValuesRepository.findAll().size();

        // Delete the temperatureValues
        restTemperatureValuesMockMvc.perform(delete("/api/temperature-values/{id}", temperatureValues.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemperatureValues> temperatureValuesList = temperatureValuesRepository.findAll();
        assertThat(temperatureValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
