package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.IndicationType;
import com.kognitic.trial.repository.IndicationTypeRepository;
import com.kognitic.trial.repository.search.IndicationTypeSearchRepository;
import com.kognitic.trial.service.IndicationTypeService;

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
 * Integration tests for the {@link IndicationTypeResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IndicationTypeResourceIT {

    private static final String DEFAULT_INDICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION_TYPE = "BBBBBBBBBB";

    @Autowired
    private IndicationTypeRepository indicationTypeRepository;

    @Autowired
    private IndicationTypeService indicationTypeService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.IndicationTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private IndicationTypeSearchRepository mockIndicationTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicationTypeMockMvc;

    private IndicationType indicationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicationType createEntity(EntityManager em) {
        IndicationType indicationType = new IndicationType()
            .indicationType(DEFAULT_INDICATION_TYPE);
        return indicationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicationType createUpdatedEntity(EntityManager em) {
        IndicationType indicationType = new IndicationType()
            .indicationType(UPDATED_INDICATION_TYPE);
        return indicationType;
    }

    @BeforeEach
    public void initTest() {
        indicationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndicationType() throws Exception {
        int databaseSizeBeforeCreate = indicationTypeRepository.findAll().size();
        // Create the IndicationType
        restIndicationTypeMockMvc.perform(post("/api/indication-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicationType)))
            .andExpect(status().isCreated());

        // Validate the IndicationType in the database
        List<IndicationType> indicationTypeList = indicationTypeRepository.findAll();
        assertThat(indicationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IndicationType testIndicationType = indicationTypeList.get(indicationTypeList.size() - 1);
        assertThat(testIndicationType.getIndicationType()).isEqualTo(DEFAULT_INDICATION_TYPE);

        // Validate the IndicationType in Elasticsearch
        verify(mockIndicationTypeSearchRepository, times(1)).save(testIndicationType);
    }

    @Test
    @Transactional
    public void createIndicationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicationTypeRepository.findAll().size();

        // Create the IndicationType with an existing ID
        indicationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicationTypeMockMvc.perform(post("/api/indication-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicationType)))
            .andExpect(status().isBadRequest());

        // Validate the IndicationType in the database
        List<IndicationType> indicationTypeList = indicationTypeRepository.findAll();
        assertThat(indicationTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the IndicationType in Elasticsearch
        verify(mockIndicationTypeSearchRepository, times(0)).save(indicationType);
    }


    @Test
    @Transactional
    public void getAllIndicationTypes() throws Exception {
        // Initialize the database
        indicationTypeRepository.saveAndFlush(indicationType);

        // Get all the indicationTypeList
        restIndicationTypeMockMvc.perform(get("/api/indication-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicationType").value(hasItem(DEFAULT_INDICATION_TYPE)));
    }
    
    @Test
    @Transactional
    public void getIndicationType() throws Exception {
        // Initialize the database
        indicationTypeRepository.saveAndFlush(indicationType);

        // Get the indicationType
        restIndicationTypeMockMvc.perform(get("/api/indication-types/{id}", indicationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicationType.getId().intValue()))
            .andExpect(jsonPath("$.indicationType").value(DEFAULT_INDICATION_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingIndicationType() throws Exception {
        // Get the indicationType
        restIndicationTypeMockMvc.perform(get("/api/indication-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndicationType() throws Exception {
        // Initialize the database
        indicationTypeService.save(indicationType);

        int databaseSizeBeforeUpdate = indicationTypeRepository.findAll().size();

        // Update the indicationType
        IndicationType updatedIndicationType = indicationTypeRepository.findById(indicationType.getId()).get();
        // Disconnect from session so that the updates on updatedIndicationType are not directly saved in db
        em.detach(updatedIndicationType);
        updatedIndicationType
            .indicationType(UPDATED_INDICATION_TYPE);

        restIndicationTypeMockMvc.perform(put("/api/indication-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndicationType)))
            .andExpect(status().isOk());

        // Validate the IndicationType in the database
        List<IndicationType> indicationTypeList = indicationTypeRepository.findAll();
        assertThat(indicationTypeList).hasSize(databaseSizeBeforeUpdate);
        IndicationType testIndicationType = indicationTypeList.get(indicationTypeList.size() - 1);
        assertThat(testIndicationType.getIndicationType()).isEqualTo(UPDATED_INDICATION_TYPE);

        // Validate the IndicationType in Elasticsearch
        verify(mockIndicationTypeSearchRepository, times(2)).save(testIndicationType);
    }

    @Test
    @Transactional
    public void updateNonExistingIndicationType() throws Exception {
        int databaseSizeBeforeUpdate = indicationTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicationTypeMockMvc.perform(put("/api/indication-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicationType)))
            .andExpect(status().isBadRequest());

        // Validate the IndicationType in the database
        List<IndicationType> indicationTypeList = indicationTypeRepository.findAll();
        assertThat(indicationTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IndicationType in Elasticsearch
        verify(mockIndicationTypeSearchRepository, times(0)).save(indicationType);
    }

    @Test
    @Transactional
    public void deleteIndicationType() throws Exception {
        // Initialize the database
        indicationTypeService.save(indicationType);

        int databaseSizeBeforeDelete = indicationTypeRepository.findAll().size();

        // Delete the indicationType
        restIndicationTypeMockMvc.perform(delete("/api/indication-types/{id}", indicationType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndicationType> indicationTypeList = indicationTypeRepository.findAll();
        assertThat(indicationTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IndicationType in Elasticsearch
        verify(mockIndicationTypeSearchRepository, times(1)).deleteById(indicationType.getId());
    }

    @Test
    @Transactional
    public void searchIndicationType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        indicationTypeService.save(indicationType);
        when(mockIndicationTypeSearchRepository.search(queryStringQuery("id:" + indicationType.getId())))
            .thenReturn(Collections.singletonList(indicationType));

        // Search the indicationType
        restIndicationTypeMockMvc.perform(get("/api/_search/indication-types?query=id:" + indicationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicationType").value(hasItem(DEFAULT_INDICATION_TYPE)));
    }
}
