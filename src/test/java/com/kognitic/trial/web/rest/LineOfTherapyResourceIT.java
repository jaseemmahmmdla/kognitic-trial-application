package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.LineOfTherapy;
import com.kognitic.trial.repository.LineOfTherapyRepository;
import com.kognitic.trial.repository.search.LineOfTherapySearchRepository;
import com.kognitic.trial.service.LineOfTherapyService;

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
 * Integration tests for the {@link LineOfTherapyResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LineOfTherapyResourceIT {

    private static final String DEFAULT_LOT = "AAAAAAAAAA";
    private static final String UPDATED_LOT = "BBBBBBBBBB";

    @Autowired
    private LineOfTherapyRepository lineOfTherapyRepository;

    @Autowired
    private LineOfTherapyService lineOfTherapyService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.LineOfTherapySearchRepositoryMockConfiguration
     */
    @Autowired
    private LineOfTherapySearchRepository mockLineOfTherapySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLineOfTherapyMockMvc;

    private LineOfTherapy lineOfTherapy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineOfTherapy createEntity(EntityManager em) {
        LineOfTherapy lineOfTherapy = new LineOfTherapy()
            .lot(DEFAULT_LOT);
        return lineOfTherapy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LineOfTherapy createUpdatedEntity(EntityManager em) {
        LineOfTherapy lineOfTherapy = new LineOfTherapy()
            .lot(UPDATED_LOT);
        return lineOfTherapy;
    }

    @BeforeEach
    public void initTest() {
        lineOfTherapy = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineOfTherapy() throws Exception {
        int databaseSizeBeforeCreate = lineOfTherapyRepository.findAll().size();
        // Create the LineOfTherapy
        restLineOfTherapyMockMvc.perform(post("/api/line-of-therapies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineOfTherapy)))
            .andExpect(status().isCreated());

        // Validate the LineOfTherapy in the database
        List<LineOfTherapy> lineOfTherapyList = lineOfTherapyRepository.findAll();
        assertThat(lineOfTherapyList).hasSize(databaseSizeBeforeCreate + 1);
        LineOfTherapy testLineOfTherapy = lineOfTherapyList.get(lineOfTherapyList.size() - 1);
        assertThat(testLineOfTherapy.getLot()).isEqualTo(DEFAULT_LOT);

        // Validate the LineOfTherapy in Elasticsearch
        verify(mockLineOfTherapySearchRepository, times(1)).save(testLineOfTherapy);
    }

    @Test
    @Transactional
    public void createLineOfTherapyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineOfTherapyRepository.findAll().size();

        // Create the LineOfTherapy with an existing ID
        lineOfTherapy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineOfTherapyMockMvc.perform(post("/api/line-of-therapies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineOfTherapy)))
            .andExpect(status().isBadRequest());

        // Validate the LineOfTherapy in the database
        List<LineOfTherapy> lineOfTherapyList = lineOfTherapyRepository.findAll();
        assertThat(lineOfTherapyList).hasSize(databaseSizeBeforeCreate);

        // Validate the LineOfTherapy in Elasticsearch
        verify(mockLineOfTherapySearchRepository, times(0)).save(lineOfTherapy);
    }


    @Test
    @Transactional
    public void getAllLineOfTherapies() throws Exception {
        // Initialize the database
        lineOfTherapyRepository.saveAndFlush(lineOfTherapy);

        // Get all the lineOfTherapyList
        restLineOfTherapyMockMvc.perform(get("/api/line-of-therapies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineOfTherapy.getId().intValue())))
            .andExpect(jsonPath("$.[*].lot").value(hasItem(DEFAULT_LOT)));
    }
    
    @Test
    @Transactional
    public void getLineOfTherapy() throws Exception {
        // Initialize the database
        lineOfTherapyRepository.saveAndFlush(lineOfTherapy);

        // Get the lineOfTherapy
        restLineOfTherapyMockMvc.perform(get("/api/line-of-therapies/{id}", lineOfTherapy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lineOfTherapy.getId().intValue()))
            .andExpect(jsonPath("$.lot").value(DEFAULT_LOT));
    }
    @Test
    @Transactional
    public void getNonExistingLineOfTherapy() throws Exception {
        // Get the lineOfTherapy
        restLineOfTherapyMockMvc.perform(get("/api/line-of-therapies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineOfTherapy() throws Exception {
        // Initialize the database
        lineOfTherapyService.save(lineOfTherapy);

        int databaseSizeBeforeUpdate = lineOfTherapyRepository.findAll().size();

        // Update the lineOfTherapy
        LineOfTherapy updatedLineOfTherapy = lineOfTherapyRepository.findById(lineOfTherapy.getId()).get();
        // Disconnect from session so that the updates on updatedLineOfTherapy are not directly saved in db
        em.detach(updatedLineOfTherapy);
        updatedLineOfTherapy
            .lot(UPDATED_LOT);

        restLineOfTherapyMockMvc.perform(put("/api/line-of-therapies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineOfTherapy)))
            .andExpect(status().isOk());

        // Validate the LineOfTherapy in the database
        List<LineOfTherapy> lineOfTherapyList = lineOfTherapyRepository.findAll();
        assertThat(lineOfTherapyList).hasSize(databaseSizeBeforeUpdate);
        LineOfTherapy testLineOfTherapy = lineOfTherapyList.get(lineOfTherapyList.size() - 1);
        assertThat(testLineOfTherapy.getLot()).isEqualTo(UPDATED_LOT);

        // Validate the LineOfTherapy in Elasticsearch
        verify(mockLineOfTherapySearchRepository, times(2)).save(testLineOfTherapy);
    }

    @Test
    @Transactional
    public void updateNonExistingLineOfTherapy() throws Exception {
        int databaseSizeBeforeUpdate = lineOfTherapyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineOfTherapyMockMvc.perform(put("/api/line-of-therapies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lineOfTherapy)))
            .andExpect(status().isBadRequest());

        // Validate the LineOfTherapy in the database
        List<LineOfTherapy> lineOfTherapyList = lineOfTherapyRepository.findAll();
        assertThat(lineOfTherapyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LineOfTherapy in Elasticsearch
        verify(mockLineOfTherapySearchRepository, times(0)).save(lineOfTherapy);
    }

    @Test
    @Transactional
    public void deleteLineOfTherapy() throws Exception {
        // Initialize the database
        lineOfTherapyService.save(lineOfTherapy);

        int databaseSizeBeforeDelete = lineOfTherapyRepository.findAll().size();

        // Delete the lineOfTherapy
        restLineOfTherapyMockMvc.perform(delete("/api/line-of-therapies/{id}", lineOfTherapy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LineOfTherapy> lineOfTherapyList = lineOfTherapyRepository.findAll();
        assertThat(lineOfTherapyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LineOfTherapy in Elasticsearch
        verify(mockLineOfTherapySearchRepository, times(1)).deleteById(lineOfTherapy.getId());
    }

    @Test
    @Transactional
    public void searchLineOfTherapy() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        lineOfTherapyService.save(lineOfTherapy);
        when(mockLineOfTherapySearchRepository.search(queryStringQuery("id:" + lineOfTherapy.getId())))
            .thenReturn(Collections.singletonList(lineOfTherapy));

        // Search the lineOfTherapy
        restLineOfTherapyMockMvc.perform(get("/api/_search/line-of-therapies?query=id:" + lineOfTherapy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineOfTherapy.getId().intValue())))
            .andExpect(jsonPath("$.[*].lot").value(hasItem(DEFAULT_LOT)));
    }
}
