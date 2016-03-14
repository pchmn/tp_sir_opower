package fr.istic.sir.web.rest;

import fr.istic.sir.Application;
import fr.istic.sir.domain.Heater;
import fr.istic.sir.repository.HeaterRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HeaterResource REST controller.
 *
 * @see HeaterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeaterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_CONSO = 1;
    private static final Integer UPDATED_CONSO = 2;

    @Inject
    private HeaterRepository heaterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeaterMockMvc;

    private Heater heater;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeaterResource heaterResource = new HeaterResource();
        ReflectionTestUtils.setField(heaterResource, "heaterRepository", heaterRepository);
        this.restHeaterMockMvc = MockMvcBuilders.standaloneSetup(heaterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heater = new Heater();
        heater.setName(DEFAULT_NAME);
        heater.setConso(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void createHeater() throws Exception {
        int databaseSizeBeforeCreate = heaterRepository.findAll().size();

        // Create the Heater

        restHeaterMockMvc.perform(post("/api/heaters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heater)))
                .andExpect(status().isCreated());

        // Validate the Heater in the database
        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeCreate + 1);
        Heater testHeater = heaters.get(heaters.size() - 1);
        assertThat(testHeater.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHeater.getConso()).isEqualTo(DEFAULT_CONSO);
    }

    @Test
    @Transactional
    public void getAllHeaters() throws Exception {
        // Initialize the database
        heaterRepository.saveAndFlush(heater);

        // Get all the heaters
        restHeaterMockMvc.perform(get("/api/heaters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heater.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].conso").value(hasItem(DEFAULT_CONSO)));
    }

    @Test
    @Transactional
    public void getHeater() throws Exception {
        // Initialize the database
        heaterRepository.saveAndFlush(heater);

        // Get the heater
        restHeaterMockMvc.perform(get("/api/heaters/{id}", heater.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heater.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.conso").value(DEFAULT_CONSO));
    }

    @Test
    @Transactional
    public void getNonExistingHeater() throws Exception {
        // Get the heater
        restHeaterMockMvc.perform(get("/api/heaters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeater() throws Exception {
        // Initialize the database
        heaterRepository.saveAndFlush(heater);

		int databaseSizeBeforeUpdate = heaterRepository.findAll().size();

        // Update the heater
        heater.setName(UPDATED_NAME);
        heater.setConso(UPDATED_CONSO);

        restHeaterMockMvc.perform(put("/api/heaters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heater)))
                .andExpect(status().isOk());

        // Validate the Heater in the database
        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeUpdate);
        Heater testHeater = heaters.get(heaters.size() - 1);
        assertThat(testHeater.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHeater.getConso()).isEqualTo(UPDATED_CONSO);
    }

    @Test
    @Transactional
    public void deleteHeater() throws Exception {
        // Initialize the database
        heaterRepository.saveAndFlush(heater);

		int databaseSizeBeforeDelete = heaterRepository.findAll().size();

        // Get the heater
        restHeaterMockMvc.perform(delete("/api/heaters/{id}", heater.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Heater> heaters = heaterRepository.findAll();
        assertThat(heaters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
