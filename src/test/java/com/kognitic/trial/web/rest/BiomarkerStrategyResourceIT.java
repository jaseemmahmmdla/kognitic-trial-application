package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.BiomarkerStrategy;
import com.kognitic.trial.repository.BiomarkerStrategyRepository;
import com.kognitic.trial.repository.search.BiomarkerStrategySearchRepository;
import com.kognitic.trial.service.BiomarkerStrategyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BiomarkerStrategyResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BiomarkerStrategyResourceIT {

    private static final String DEFAULT_BIOMARKER_STRATEGY = "AAAAAAAAAA";
    private static final String UPDATED_BIOMARKER_STRATEGY = "BBBBBBBBBB";

    @Autowired
    private BiomarkerStrategyRepository biomarkerStrategyRepository;

    @Autowired
    private BiomarkerStrategyService biomarkerStrategyService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.BiomarkerStrategySearchRepositoryMockConfiguration
     */
    @Autowired
    private BiomarkerStrategySearchRepository mockBiomarkerStrategySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBiomarkerStrategyMockMvc;

    private BiomarkerStrategy biomarkerStrategy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BiomarkerStrategy createEntity(EntityManager em) {
        BiomarkerStrategy biomarkerStrategy = new BiomarkerStrategy()
            .biomarkerStrategy(DEFAULT_BIOMARKER_STRATEGY);
        return biomarkerStrategy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BiomarkerStrategy createUpdatedEntity(EntityManager em) {
        BiomarkerStrategy biomarkerStrategy = new BiomarkerStrategy()
            .biomarkerStrategy(UPDATED_BIOMARKER_STRATEGY);
        return biomarkerStrategy;
    }

    @BeforeEach
    public void initTest() {
        biomarkerStrategy = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiomarkerStrategy() throws Exception {
        int databaseSizeBeforeCreate = biomarkerStrategyRepository.findAll().size();
        // Create the BiomarkerStrategy
        restBiomarkerStrategyMockMvc.perform(post("/api/biomarker-strategies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarkerStrategy)))
            .andExpect(status().isCreated());

        // Validate the BiomarkerStrategy in the database
        List<BiomarkerStrategy> biomarkerStrategyList = biomarkerStrategyRepository.findAll();
        assertThat(biomarkerStrategyList).hasSize(databaseSizeBeforeCreate + 1);
        BiomarkerStrategy testBiomarkerStrategy = biomarkerStrategyList.get(biomarkerStrategyList.size() - 1);
        assertThat(testBiomarkerStrategy.getBiomarkerStrategy()).isEqualTo(DEFAULT_BIOMARKER_STRATEGY);

        // Validate the BiomarkerStrategy in Elasticsearch
        verify(mockBiomarkerStrategySearchRepository, times(1)).save(testBiomarkerStrategy);
    }

    @Test
    @Transactional
    public void createBiomarkerStrategyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biomarkerStrategyRepository.findAll().size();

        // Create the BiomarkerStrategy with an existing ID
        biomarkerStrategy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiomarkerStrategyMockMvc.perform(post("/api/biomarker-strategies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarkerStrategy)))
            .andExpect(status().isBadRequest());

        // Validate the BiomarkerStrategy in the database
        List<BiomarkerStrategy> biomarkerStrategyList = biomarkerStrategyRepository.findAll();
        assertThat(biomarkerStrategyList).hasSize(databaseSizeBeforeCreate);

        // Validate the BiomarkerStrategy in Elasticsearch
        verify(mockBiomarkerStrategySearchRepository, times(0)).save(biomarkerStrategy);
    }


    @Test
    @Transactional
    public void getAllBiomarkerStrategies() throws Exception {
        // Initialize the database
        biomarkerStrategyRepository.saveAndFlush(biomarkerStrategy);

        // Get all the biomarkerStrategyList
        restBiomarkerStrategyMockMvc.perform(get("/api/biomarker-strategies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biomarkerStrategy.getId().intValue())))
            .andExpect(jsonPath("$.[*].biomarkerStrategy").value(hasItem(DEFAULT_BIOMARKER_STRATEGY)));
    }
    
    @Test
    @Transactional
    public void getBiomarkerStrategy() throws Exception {
        // Initialize the database
        biomarkerStrategyRepository.saveAndFlush(biomarkerStrategy);

        // Get the biomarkerStrategy
        restBiomarkerStrategyMockMvc.perform(get("/api/biomarker-strategies/{id}", biomarkerStrategy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biomarkerStrategy.getId().intValue()))
            .andExpect(jsonPath("$.biomarkerStrategy").value(DEFAULT_BIOMARKER_STRATEGY));
    }
    @Test
    @Transactional
    public void getNonExistingBiomarkerStrategy() throws Exception {
        // Get the biomarkerStrategy
        restBiomarkerStrategyMockMvc.perform(get("/api/biomarker-strategies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiomarkerStrategy() throws Exception {
        // Initialize the database
        biomarkerStrategyService.save(biomarkerStrategy);

        int databaseSizeBeforeUpdate = biomarkerStrategyRepository.findAll().size();

        // Update the biomarkerStrategy
        BiomarkerStrategy updatedBiomarkerStrategy = biomarkerStrategyRepository.findById(biomarkerStrategy.getId()).get();
        // Disconnect from session so that the updates on updatedBiomarkerStrategy are not directly saved in db
        em.detach(updatedBiomarkerStrategy);
        updatedBiomarkerStrategy
            .biomarkerStrategy(UPDATED_BIOMARKER_STRATEGY);

        restBiomarkerStrategyMockMvc.perform(put("/api/biomarker-strategies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBiomarkerStrategy)))
            .andExpect(status().isOk());

        // Validate the BiomarkerStrategy in the database
        List<BiomarkerStrategy> biomarkerStrategyList = biomarkerStrategyRepository.findAll();
        assertThat(biomarkerStrategyList).hasSize(databaseSizeBeforeUpdate);
        BiomarkerStrategy testBiomarkerStrategy = biomarkerStrategyList.get(biomarkerStrategyList.size() - 1);
        assertThat(testBiomarkerStrategy.getBiomarkerStrategy()).isEqualTo(UPDATED_BIOMARKER_STRATEGY);

        // Validate the BiomarkerStrategy in Elasticsearch
        verify(mockBiomarkerStrategySearchRepository, times(2)).save(testBiomarkerStrategy);
    }

    @Test
    @Transactional
    public void updateNonExistingBiomarkerStrategy() throws Exception {
        int databaseSizeBeforeUpdate = biomarkerStrategyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiomarkerStrategyMockMvc.perform(put("/api/biomarker-strategies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarkerStrategy)))
            .andExpect(status().isBadRequest());

        // Validate the BiomarkerStrategy in the database
        List<BiomarkerStrategy> biomarkerStrategyList = biomarkerStrategyRepository.findAll();
        assertThat(biomarkerStrategyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BiomarkerStrategy in Elasticsearch
        verify(mockBiomarkerStrategySearchRepository, times(0)).save(biomarkerStrategy);
    }

    @Test
    @Transactional
    public void deleteBiomarkerStrategy() throws Exception {
        // Initialize the database
        biomarkerStrategyService.save(biomarkerStrategy);

        int databaseSizeBeforeDelete = biomarkerStrategyRepository.findAll().size();

        // Delete the biomarkerStrategy
        restBiomarkerStrategyMockMvc.perform(delete("/api/biomarker-strategies/{id}", biomarkerStrategy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BiomarkerStrategy> biomarkerStrategyList = biomarkerStrategyRepository.findAll();
        assertThat(biomarkerStrategyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BiomarkerStrategy in Elasticsearch
        verify(mockBiomarkerStrategySearchRepository, times(1)).deleteById(biomarkerStrategy.getId());
    }

    @Test
    @Transactional
    public void searchBiomarkerStrategy() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        biomarkerStrategyService.save(biomarkerStrategy);
        when(mockBiomarkerStrategySearchRepository.search(queryStringQuery("id:" + biomarkerStrategy.getId())))
            .thenReturn(Collections.singletonList(biomarkerStrategy));

        // Search the biomarkerStrategy
        restBiomarkerStrategyMockMvc.perform(get("/api/_search/biomarker-strategies?query=id:" + biomarkerStrategy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biomarkerStrategy.getId().intValue())))
            .andExpect(jsonPath("$.[*].biomarkerStrategy").value(hasItem(DEFAULT_BIOMARKER_STRATEGY)));
    }
}
