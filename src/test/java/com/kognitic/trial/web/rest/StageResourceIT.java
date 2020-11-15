package com.kognitic.trial.web.rest;

import com.kognitic.trial.KogniticApplicationApp;
import com.kognitic.trial.domain.Stage;
import com.kognitic.trial.repository.StageRepository;
import com.kognitic.trial.repository.search.StageSearchRepository;
import com.kognitic.trial.service.StageService;

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
 * Integration tests for the {@link StageResource} REST controller.
 */
@SpringBootTest(classes = KogniticApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StageResourceIT {

    private static final String DEFAULT_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_STAGE = "BBBBBBBBBB";

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private StageService stageService;

    /**
     * This repository is mocked in the com.kognitic.trial.repository.search test package.
     *
     * @see com.kognitic.trial.repository.search.StageSearchRepositoryMockConfiguration
     */
    @Autowired
    private StageSearchRepository mockStageSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStageMockMvc;

    private Stage stage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage()
            .stage(DEFAULT_STAGE);
        return stage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createUpdatedEntity(EntityManager em) {
        Stage stage = new Stage()
            .stage(UPDATED_STAGE);
        return stage;
    }

    @BeforeEach
    public void initTest() {
        stage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();
        // Create the Stage
        restStageMockMvc.perform(post("/api/stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stage)))
            .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getStage()).isEqualTo(DEFAULT_STAGE);

        // Validate the Stage in Elasticsearch
        verify(mockStageSearchRepository, times(1)).save(testStage);
    }

    @Test
    @Transactional
    public void createStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage with an existing ID
        stage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStageMockMvc.perform(post("/api/stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stage)))
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Stage in Elasticsearch
        verify(mockStageSearchRepository, times(0)).save(stage);
    }


    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stageList
        restStageMockMvc.perform(get("/api/stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE)));
    }
    
    @Test
    @Transactional
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.stage").value(DEFAULT_STAGE));
    }
    @Test
    @Transactional
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
        stageService.save(stage);

        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findById(stage.getId()).get();
        // Disconnect from session so that the updates on updatedStage are not directly saved in db
        em.detach(updatedStage);
        updatedStage
            .stage(UPDATED_STAGE);

        restStageMockMvc.perform(put("/api/stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStage)))
            .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getStage()).isEqualTo(UPDATED_STAGE);

        // Validate the Stage in Elasticsearch
        verify(mockStageSearchRepository, times(2)).save(testStage);
    }

    @Test
    @Transactional
    public void updateNonExistingStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStageMockMvc.perform(put("/api/stages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stage)))
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Stage in Elasticsearch
        verify(mockStageSearchRepository, times(0)).save(stage);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
        stageService.save(stage);

        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Delete the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Stage in Elasticsearch
        verify(mockStageSearchRepository, times(1)).deleteById(stage.getId());
    }

    @Test
    @Transactional
    public void searchStage() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        stageService.save(stage);
        when(mockStageSearchRepository.search(queryStringQuery("id:" + stage.getId())))
            .thenReturn(Collections.singletonList(stage));

        // Search the stage
        restStageMockMvc.perform(get("/api/_search/stages?query=id:" + stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE)));
    }
}
