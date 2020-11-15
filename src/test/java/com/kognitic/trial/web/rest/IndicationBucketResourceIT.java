package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.IndicationBucket;
import com.kognitic.trial.repository.IndicationBucketRepository;
import com.kognitic.trial.repository.search.IndicationBucketSearchRepository;
import com.kognitic.trial.service.IndicationBucketService;

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
 * Integration tests for the {@link IndicationBucketResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IndicationBucketResourceIT {

    private static final String DEFAULT_INDICATION_BUCKET = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION_BUCKET = "BBBBBBBBBB";

    @Autowired
    private IndicationBucketRepository indicationBucketRepository;

    @Autowired
    private IndicationBucketService indicationBucketService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.IndicationBucketSearchRepositoryMockConfiguration
     */
    @Autowired
    private IndicationBucketSearchRepository mockIndicationBucketSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicationBucketMockMvc;

    private IndicationBucket indicationBucket;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicationBucket createEntity(EntityManager em) {
        IndicationBucket indicationBucket = new IndicationBucket()
            .indicationBucket(DEFAULT_INDICATION_BUCKET);
        return indicationBucket;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicationBucket createUpdatedEntity(EntityManager em) {
        IndicationBucket indicationBucket = new IndicationBucket()
            .indicationBucket(UPDATED_INDICATION_BUCKET);
        return indicationBucket;
    }

    @BeforeEach
    public void initTest() {
        indicationBucket = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndicationBucket() throws Exception {
        int databaseSizeBeforeCreate = indicationBucketRepository.findAll().size();
        // Create the IndicationBucket
        restIndicationBucketMockMvc.perform(post("/api/indication-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicationBucket)))
            .andExpect(status().isCreated());

        // Validate the IndicationBucket in the database
        List<IndicationBucket> indicationBucketList = indicationBucketRepository.findAll();
        assertThat(indicationBucketList).hasSize(databaseSizeBeforeCreate + 1);
        IndicationBucket testIndicationBucket = indicationBucketList.get(indicationBucketList.size() - 1);
        assertThat(testIndicationBucket.getIndicationBucket()).isEqualTo(DEFAULT_INDICATION_BUCKET);

        // Validate the IndicationBucket in Elasticsearch
        verify(mockIndicationBucketSearchRepository, times(1)).save(testIndicationBucket);
    }

    @Test
    @Transactional
    public void createIndicationBucketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicationBucketRepository.findAll().size();

        // Create the IndicationBucket with an existing ID
        indicationBucket.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicationBucketMockMvc.perform(post("/api/indication-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicationBucket)))
            .andExpect(status().isBadRequest());

        // Validate the IndicationBucket in the database
        List<IndicationBucket> indicationBucketList = indicationBucketRepository.findAll();
        assertThat(indicationBucketList).hasSize(databaseSizeBeforeCreate);

        // Validate the IndicationBucket in Elasticsearch
        verify(mockIndicationBucketSearchRepository, times(0)).save(indicationBucket);
    }


    @Test
    @Transactional
    public void getAllIndicationBuckets() throws Exception {
        // Initialize the database
        indicationBucketRepository.saveAndFlush(indicationBucket);

        // Get all the indicationBucketList
        restIndicationBucketMockMvc.perform(get("/api/indication-buckets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicationBucket.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicationBucket").value(hasItem(DEFAULT_INDICATION_BUCKET)));
    }
    
    @Test
    @Transactional
    public void getIndicationBucket() throws Exception {
        // Initialize the database
        indicationBucketRepository.saveAndFlush(indicationBucket);

        // Get the indicationBucket
        restIndicationBucketMockMvc.perform(get("/api/indication-buckets/{id}", indicationBucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicationBucket.getId().intValue()))
            .andExpect(jsonPath("$.indicationBucket").value(DEFAULT_INDICATION_BUCKET));
    }
    @Test
    @Transactional
    public void getNonExistingIndicationBucket() throws Exception {
        // Get the indicationBucket
        restIndicationBucketMockMvc.perform(get("/api/indication-buckets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndicationBucket() throws Exception {
        // Initialize the database
        indicationBucketService.save(indicationBucket);

        int databaseSizeBeforeUpdate = indicationBucketRepository.findAll().size();

        // Update the indicationBucket
        IndicationBucket updatedIndicationBucket = indicationBucketRepository.findById(indicationBucket.getId()).get();
        // Disconnect from session so that the updates on updatedIndicationBucket are not directly saved in db
        em.detach(updatedIndicationBucket);
        updatedIndicationBucket
            .indicationBucket(UPDATED_INDICATION_BUCKET);

        restIndicationBucketMockMvc.perform(put("/api/indication-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndicationBucket)))
            .andExpect(status().isOk());

        // Validate the IndicationBucket in the database
        List<IndicationBucket> indicationBucketList = indicationBucketRepository.findAll();
        assertThat(indicationBucketList).hasSize(databaseSizeBeforeUpdate);
        IndicationBucket testIndicationBucket = indicationBucketList.get(indicationBucketList.size() - 1);
        assertThat(testIndicationBucket.getIndicationBucket()).isEqualTo(UPDATED_INDICATION_BUCKET);

        // Validate the IndicationBucket in Elasticsearch
        verify(mockIndicationBucketSearchRepository, times(2)).save(testIndicationBucket);
    }

    @Test
    @Transactional
    public void updateNonExistingIndicationBucket() throws Exception {
        int databaseSizeBeforeUpdate = indicationBucketRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicationBucketMockMvc.perform(put("/api/indication-buckets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicationBucket)))
            .andExpect(status().isBadRequest());

        // Validate the IndicationBucket in the database
        List<IndicationBucket> indicationBucketList = indicationBucketRepository.findAll();
        assertThat(indicationBucketList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IndicationBucket in Elasticsearch
        verify(mockIndicationBucketSearchRepository, times(0)).save(indicationBucket);
    }

    @Test
    @Transactional
    public void deleteIndicationBucket() throws Exception {
        // Initialize the database
        indicationBucketService.save(indicationBucket);

        int databaseSizeBeforeDelete = indicationBucketRepository.findAll().size();

        // Delete the indicationBucket
        restIndicationBucketMockMvc.perform(delete("/api/indication-buckets/{id}", indicationBucket.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndicationBucket> indicationBucketList = indicationBucketRepository.findAll();
        assertThat(indicationBucketList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IndicationBucket in Elasticsearch
        verify(mockIndicationBucketSearchRepository, times(1)).deleteById(indicationBucket.getId());
    }

    @Test
    @Transactional
    public void searchIndicationBucket() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        indicationBucketService.save(indicationBucket);
        when(mockIndicationBucketSearchRepository.search(queryStringQuery("id:" + indicationBucket.getId())))
            .thenReturn(Collections.singletonList(indicationBucket));

        // Search the indicationBucket
        restIndicationBucketMockMvc.perform(get("/api/_search/indication-buckets?query=id:" + indicationBucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicationBucket.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicationBucket").value(hasItem(DEFAULT_INDICATION_BUCKET)));
    }
}
