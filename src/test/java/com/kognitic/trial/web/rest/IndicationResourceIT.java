package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.Indication;
import com.kognitic.trial.repository.IndicationRepository;
import com.kognitic.trial.repository.search.IndicationSearchRepository;
import com.kognitic.trial.service.IndicationService;

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
 * Integration tests for the {@link IndicationResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IndicationResourceIT {

    private static final String DEFAULT_INDICATION = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION = "BBBBBBBBBB";

    @Autowired
    private IndicationRepository indicationRepository;

    @Autowired
    private IndicationService indicationService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.IndicationSearchRepositoryMockConfiguration
     */
    @Autowired
    private IndicationSearchRepository mockIndicationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicationMockMvc;

    private Indication indication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indication createEntity(EntityManager em) {
        Indication indication = new Indication()
            .indication(DEFAULT_INDICATION);
        return indication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indication createUpdatedEntity(EntityManager em) {
        Indication indication = new Indication()
            .indication(UPDATED_INDICATION);
        return indication;
    }

    @BeforeEach
    public void initTest() {
        indication = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndication() throws Exception {
        int databaseSizeBeforeCreate = indicationRepository.findAll().size();
        // Create the Indication
        restIndicationMockMvc.perform(post("/api/indications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indication)))
            .andExpect(status().isCreated());

        // Validate the Indication in the database
        List<Indication> indicationList = indicationRepository.findAll();
        assertThat(indicationList).hasSize(databaseSizeBeforeCreate + 1);
        Indication testIndication = indicationList.get(indicationList.size() - 1);
        assertThat(testIndication.getIndication()).isEqualTo(DEFAULT_INDICATION);

        // Validate the Indication in Elasticsearch
        verify(mockIndicationSearchRepository, times(1)).save(testIndication);
    }

    @Test
    @Transactional
    public void createIndicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicationRepository.findAll().size();

        // Create the Indication with an existing ID
        indication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicationMockMvc.perform(post("/api/indications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indication)))
            .andExpect(status().isBadRequest());

        // Validate the Indication in the database
        List<Indication> indicationList = indicationRepository.findAll();
        assertThat(indicationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Indication in Elasticsearch
        verify(mockIndicationSearchRepository, times(0)).save(indication);
    }


    @Test
    @Transactional
    public void getAllIndications() throws Exception {
        // Initialize the database
        indicationRepository.saveAndFlush(indication);

        // Get all the indicationList
        restIndicationMockMvc.perform(get("/api/indications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indication.getId().intValue())))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)));
    }
    
    @Test
    @Transactional
    public void getIndication() throws Exception {
        // Initialize the database
        indicationRepository.saveAndFlush(indication);

        // Get the indication
        restIndicationMockMvc.perform(get("/api/indications/{id}", indication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indication.getId().intValue()))
            .andExpect(jsonPath("$.indication").value(DEFAULT_INDICATION));
    }
    @Test
    @Transactional
    public void getNonExistingIndication() throws Exception {
        // Get the indication
        restIndicationMockMvc.perform(get("/api/indications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndication() throws Exception {
        // Initialize the database
        indicationService.save(indication);

        int databaseSizeBeforeUpdate = indicationRepository.findAll().size();

        // Update the indication
        Indication updatedIndication = indicationRepository.findById(indication.getId()).get();
        // Disconnect from session so that the updates on updatedIndication are not directly saved in db
        em.detach(updatedIndication);
        updatedIndication
            .indication(UPDATED_INDICATION);

        restIndicationMockMvc.perform(put("/api/indications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndication)))
            .andExpect(status().isOk());

        // Validate the Indication in the database
        List<Indication> indicationList = indicationRepository.findAll();
        assertThat(indicationList).hasSize(databaseSizeBeforeUpdate);
        Indication testIndication = indicationList.get(indicationList.size() - 1);
        assertThat(testIndication.getIndication()).isEqualTo(UPDATED_INDICATION);

        // Validate the Indication in Elasticsearch
        verify(mockIndicationSearchRepository, times(2)).save(testIndication);
    }

    @Test
    @Transactional
    public void updateNonExistingIndication() throws Exception {
        int databaseSizeBeforeUpdate = indicationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicationMockMvc.perform(put("/api/indications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indication)))
            .andExpect(status().isBadRequest());

        // Validate the Indication in the database
        List<Indication> indicationList = indicationRepository.findAll();
        assertThat(indicationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Indication in Elasticsearch
        verify(mockIndicationSearchRepository, times(0)).save(indication);
    }

    @Test
    @Transactional
    public void deleteIndication() throws Exception {
        // Initialize the database
        indicationService.save(indication);

        int databaseSizeBeforeDelete = indicationRepository.findAll().size();

        // Delete the indication
        restIndicationMockMvc.perform(delete("/api/indications/{id}", indication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Indication> indicationList = indicationRepository.findAll();
        assertThat(indicationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Indication in Elasticsearch
        verify(mockIndicationSearchRepository, times(1)).deleteById(indication.getId());
    }

    @Test
    @Transactional
    public void searchIndication() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        indicationService.save(indication);
        when(mockIndicationSearchRepository.search(queryStringQuery("id:" + indication.getId())))
            .thenReturn(Collections.singletonList(indication));

        // Search the indication
        restIndicationMockMvc.perform(get("/api/_search/indications?query=id:" + indication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indication.getId().intValue())))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)));
    }
}
