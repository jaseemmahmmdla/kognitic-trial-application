package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.Biomarker;
import com.kognitic.trial.repository.BiomarkerRepository;
import com.kognitic.trial.repository.search.BiomarkerSearchRepository;
import com.kognitic.trial.service.BiomarkerService;

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
 * Integration tests for the {@link BiomarkerResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BiomarkerResourceIT {

    private static final String DEFAULT_BIOMARKER = "AAAAAAAAAA";
    private static final String UPDATED_BIOMARKER = "BBBBBBBBBB";

    @Autowired
    private BiomarkerRepository biomarkerRepository;

    @Autowired
    private BiomarkerService biomarkerService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.BiomarkerSearchRepositoryMockConfiguration
     */
    @Autowired
    private BiomarkerSearchRepository mockBiomarkerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBiomarkerMockMvc;

    private Biomarker biomarker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biomarker createEntity(EntityManager em) {
        Biomarker biomarker = new Biomarker()
            .biomarker(DEFAULT_BIOMARKER);
        return biomarker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biomarker createUpdatedEntity(EntityManager em) {
        Biomarker biomarker = new Biomarker()
            .biomarker(UPDATED_BIOMARKER);
        return biomarker;
    }

    @BeforeEach
    public void initTest() {
        biomarker = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiomarker() throws Exception {
        int databaseSizeBeforeCreate = biomarkerRepository.findAll().size();
        // Create the Biomarker
        restBiomarkerMockMvc.perform(post("/api/biomarkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarker)))
            .andExpect(status().isCreated());

        // Validate the Biomarker in the database
        List<Biomarker> biomarkerList = biomarkerRepository.findAll();
        assertThat(biomarkerList).hasSize(databaseSizeBeforeCreate + 1);
        Biomarker testBiomarker = biomarkerList.get(biomarkerList.size() - 1);
        assertThat(testBiomarker.getBiomarker()).isEqualTo(DEFAULT_BIOMARKER);

        // Validate the Biomarker in Elasticsearch
        verify(mockBiomarkerSearchRepository, times(1)).save(testBiomarker);
    }

    @Test
    @Transactional
    public void createBiomarkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biomarkerRepository.findAll().size();

        // Create the Biomarker with an existing ID
        biomarker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiomarkerMockMvc.perform(post("/api/biomarkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarker)))
            .andExpect(status().isBadRequest());

        // Validate the Biomarker in the database
        List<Biomarker> biomarkerList = biomarkerRepository.findAll();
        assertThat(biomarkerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Biomarker in Elasticsearch
        verify(mockBiomarkerSearchRepository, times(0)).save(biomarker);
    }


    @Test
    @Transactional
    public void getAllBiomarkers() throws Exception {
        // Initialize the database
        biomarkerRepository.saveAndFlush(biomarker);

        // Get all the biomarkerList
        restBiomarkerMockMvc.perform(get("/api/biomarkers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biomarker.getId().intValue())))
            .andExpect(jsonPath("$.[*].biomarker").value(hasItem(DEFAULT_BIOMARKER)));
    }
    
    @Test
    @Transactional
    public void getBiomarker() throws Exception {
        // Initialize the database
        biomarkerRepository.saveAndFlush(biomarker);

        // Get the biomarker
        restBiomarkerMockMvc.perform(get("/api/biomarkers/{id}", biomarker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biomarker.getId().intValue()))
            .andExpect(jsonPath("$.biomarker").value(DEFAULT_BIOMARKER));
    }
    @Test
    @Transactional
    public void getNonExistingBiomarker() throws Exception {
        // Get the biomarker
        restBiomarkerMockMvc.perform(get("/api/biomarkers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiomarker() throws Exception {
        // Initialize the database
        biomarkerService.save(biomarker);

        int databaseSizeBeforeUpdate = biomarkerRepository.findAll().size();

        // Update the biomarker
        Biomarker updatedBiomarker = biomarkerRepository.findById(biomarker.getId()).get();
        // Disconnect from session so that the updates on updatedBiomarker are not directly saved in db
        em.detach(updatedBiomarker);
        updatedBiomarker
            .biomarker(UPDATED_BIOMARKER);

        restBiomarkerMockMvc.perform(put("/api/biomarkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBiomarker)))
            .andExpect(status().isOk());

        // Validate the Biomarker in the database
        List<Biomarker> biomarkerList = biomarkerRepository.findAll();
        assertThat(biomarkerList).hasSize(databaseSizeBeforeUpdate);
        Biomarker testBiomarker = biomarkerList.get(biomarkerList.size() - 1);
        assertThat(testBiomarker.getBiomarker()).isEqualTo(UPDATED_BIOMARKER);

        // Validate the Biomarker in Elasticsearch
        verify(mockBiomarkerSearchRepository, times(2)).save(testBiomarker);
    }

    @Test
    @Transactional
    public void updateNonExistingBiomarker() throws Exception {
        int databaseSizeBeforeUpdate = biomarkerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiomarkerMockMvc.perform(put("/api/biomarkers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarker)))
            .andExpect(status().isBadRequest());

        // Validate the Biomarker in the database
        List<Biomarker> biomarkerList = biomarkerRepository.findAll();
        assertThat(biomarkerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Biomarker in Elasticsearch
        verify(mockBiomarkerSearchRepository, times(0)).save(biomarker);
    }

    @Test
    @Transactional
    public void deleteBiomarker() throws Exception {
        // Initialize the database
        biomarkerService.save(biomarker);

        int databaseSizeBeforeDelete = biomarkerRepository.findAll().size();

        // Delete the biomarker
        restBiomarkerMockMvc.perform(delete("/api/biomarkers/{id}", biomarker.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Biomarker> biomarkerList = biomarkerRepository.findAll();
        assertThat(biomarkerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Biomarker in Elasticsearch
        verify(mockBiomarkerSearchRepository, times(1)).deleteById(biomarker.getId());
    }

    @Test
    @Transactional
    public void searchBiomarker() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        biomarkerService.save(biomarker);
        when(mockBiomarkerSearchRepository.search(queryStringQuery("id:" + biomarker.getId())))
            .thenReturn(Collections.singletonList(biomarker));

        // Search the biomarker
        restBiomarkerMockMvc.perform(get("/api/_search/biomarkers?query=id:" + biomarker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biomarker.getId().intValue())))
            .andExpect(jsonPath("$.[*].biomarker").value(hasItem(DEFAULT_BIOMARKER)));
    }
}
