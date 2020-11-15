package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.BiomarkerMutation;
import com.kognitic.trial.repository.BiomarkerMutationRepository;
import com.kognitic.trial.repository.search.BiomarkerMutationSearchRepository;
import com.kognitic.trial.service.BiomarkerMutationService;

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
 * Integration tests for the {@link BiomarkerMutationResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BiomarkerMutationResourceIT {

    private static final String DEFAULT_BIOMARKER_MUTATION = "AAAAAAAAAA";
    private static final String UPDATED_BIOMARKER_MUTATION = "BBBBBBBBBB";

    @Autowired
    private BiomarkerMutationRepository biomarkerMutationRepository;

    @Autowired
    private BiomarkerMutationService biomarkerMutationService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.BiomarkerMutationSearchRepositoryMockConfiguration
     */
    @Autowired
    private BiomarkerMutationSearchRepository mockBiomarkerMutationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBiomarkerMutationMockMvc;

    private BiomarkerMutation biomarkerMutation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BiomarkerMutation createEntity(EntityManager em) {
        BiomarkerMutation biomarkerMutation = new BiomarkerMutation()
            .biomarkerMutation(DEFAULT_BIOMARKER_MUTATION);
        return biomarkerMutation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BiomarkerMutation createUpdatedEntity(EntityManager em) {
        BiomarkerMutation biomarkerMutation = new BiomarkerMutation()
            .biomarkerMutation(UPDATED_BIOMARKER_MUTATION);
        return biomarkerMutation;
    }

    @BeforeEach
    public void initTest() {
        biomarkerMutation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiomarkerMutation() throws Exception {
        int databaseSizeBeforeCreate = biomarkerMutationRepository.findAll().size();
        // Create the BiomarkerMutation
        restBiomarkerMutationMockMvc.perform(post("/api/biomarker-mutations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarkerMutation)))
            .andExpect(status().isCreated());

        // Validate the BiomarkerMutation in the database
        List<BiomarkerMutation> biomarkerMutationList = biomarkerMutationRepository.findAll();
        assertThat(biomarkerMutationList).hasSize(databaseSizeBeforeCreate + 1);
        BiomarkerMutation testBiomarkerMutation = biomarkerMutationList.get(biomarkerMutationList.size() - 1);
        assertThat(testBiomarkerMutation.getBiomarkerMutation()).isEqualTo(DEFAULT_BIOMARKER_MUTATION);

        // Validate the BiomarkerMutation in Elasticsearch
        verify(mockBiomarkerMutationSearchRepository, times(1)).save(testBiomarkerMutation);
    }

    @Test
    @Transactional
    public void createBiomarkerMutationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biomarkerMutationRepository.findAll().size();

        // Create the BiomarkerMutation with an existing ID
        biomarkerMutation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiomarkerMutationMockMvc.perform(post("/api/biomarker-mutations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarkerMutation)))
            .andExpect(status().isBadRequest());

        // Validate the BiomarkerMutation in the database
        List<BiomarkerMutation> biomarkerMutationList = biomarkerMutationRepository.findAll();
        assertThat(biomarkerMutationList).hasSize(databaseSizeBeforeCreate);

        // Validate the BiomarkerMutation in Elasticsearch
        verify(mockBiomarkerMutationSearchRepository, times(0)).save(biomarkerMutation);
    }


    @Test
    @Transactional
    public void getAllBiomarkerMutations() throws Exception {
        // Initialize the database
        biomarkerMutationRepository.saveAndFlush(biomarkerMutation);

        // Get all the biomarkerMutationList
        restBiomarkerMutationMockMvc.perform(get("/api/biomarker-mutations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biomarkerMutation.getId().intValue())))
            .andExpect(jsonPath("$.[*].biomarkerMutation").value(hasItem(DEFAULT_BIOMARKER_MUTATION)));
    }
    
    @Test
    @Transactional
    public void getBiomarkerMutation() throws Exception {
        // Initialize the database
        biomarkerMutationRepository.saveAndFlush(biomarkerMutation);

        // Get the biomarkerMutation
        restBiomarkerMutationMockMvc.perform(get("/api/biomarker-mutations/{id}", biomarkerMutation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biomarkerMutation.getId().intValue()))
            .andExpect(jsonPath("$.biomarkerMutation").value(DEFAULT_BIOMARKER_MUTATION));
    }
    @Test
    @Transactional
    public void getNonExistingBiomarkerMutation() throws Exception {
        // Get the biomarkerMutation
        restBiomarkerMutationMockMvc.perform(get("/api/biomarker-mutations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiomarkerMutation() throws Exception {
        // Initialize the database
        biomarkerMutationService.save(biomarkerMutation);

        int databaseSizeBeforeUpdate = biomarkerMutationRepository.findAll().size();

        // Update the biomarkerMutation
        BiomarkerMutation updatedBiomarkerMutation = biomarkerMutationRepository.findById(biomarkerMutation.getId()).get();
        // Disconnect from session so that the updates on updatedBiomarkerMutation are not directly saved in db
        em.detach(updatedBiomarkerMutation);
        updatedBiomarkerMutation
            .biomarkerMutation(UPDATED_BIOMARKER_MUTATION);

        restBiomarkerMutationMockMvc.perform(put("/api/biomarker-mutations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBiomarkerMutation)))
            .andExpect(status().isOk());

        // Validate the BiomarkerMutation in the database
        List<BiomarkerMutation> biomarkerMutationList = biomarkerMutationRepository.findAll();
        assertThat(biomarkerMutationList).hasSize(databaseSizeBeforeUpdate);
        BiomarkerMutation testBiomarkerMutation = biomarkerMutationList.get(biomarkerMutationList.size() - 1);
        assertThat(testBiomarkerMutation.getBiomarkerMutation()).isEqualTo(UPDATED_BIOMARKER_MUTATION);

        // Validate the BiomarkerMutation in Elasticsearch
        verify(mockBiomarkerMutationSearchRepository, times(2)).save(testBiomarkerMutation);
    }

    @Test
    @Transactional
    public void updateNonExistingBiomarkerMutation() throws Exception {
        int databaseSizeBeforeUpdate = biomarkerMutationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiomarkerMutationMockMvc.perform(put("/api/biomarker-mutations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biomarkerMutation)))
            .andExpect(status().isBadRequest());

        // Validate the BiomarkerMutation in the database
        List<BiomarkerMutation> biomarkerMutationList = biomarkerMutationRepository.findAll();
        assertThat(biomarkerMutationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BiomarkerMutation in Elasticsearch
        verify(mockBiomarkerMutationSearchRepository, times(0)).save(biomarkerMutation);
    }

    @Test
    @Transactional
    public void deleteBiomarkerMutation() throws Exception {
        // Initialize the database
        biomarkerMutationService.save(biomarkerMutation);

        int databaseSizeBeforeDelete = biomarkerMutationRepository.findAll().size();

        // Delete the biomarkerMutation
        restBiomarkerMutationMockMvc.perform(delete("/api/biomarker-mutations/{id}", biomarkerMutation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BiomarkerMutation> biomarkerMutationList = biomarkerMutationRepository.findAll();
        assertThat(biomarkerMutationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BiomarkerMutation in Elasticsearch
        verify(mockBiomarkerMutationSearchRepository, times(1)).deleteById(biomarkerMutation.getId());
    }

    @Test
    @Transactional
    public void searchBiomarkerMutation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        biomarkerMutationService.save(biomarkerMutation);
        when(mockBiomarkerMutationSearchRepository.search(queryStringQuery("id:" + biomarkerMutation.getId())))
            .thenReturn(Collections.singletonList(biomarkerMutation));

        // Search the biomarkerMutation
        restBiomarkerMutationMockMvc.perform(get("/api/_search/biomarker-mutations?query=id:" + biomarkerMutation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biomarkerMutation.getId().intValue())))
            .andExpect(jsonPath("$.[*].biomarkerMutation").value(hasItem(DEFAULT_BIOMARKER_MUTATION)));
    }
}
