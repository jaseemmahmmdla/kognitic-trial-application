package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.Stage;
import com.kognitic.trial.service.StageService;
import com.kognitic.trial.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.kognitic.trial.domain.Stage}.
 */
@RestController
@RequestMapping("/api")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);

    private static final String ENTITY_NAME = "stage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StageService stageService;

    public StageResource(StageService stageService) {
        this.stageService = stageService;
    }

    /**
     * {@code POST  /stages} : Create a new stage.
     *
     * @param stage the stage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stage, or with status {@code 400 (Bad Request)} if the stage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stages")
    public ResponseEntity<Stage> createStage(@RequestBody Stage stage) throws URISyntaxException {
        log.debug("REST request to save Stage : {}", stage);
        if (stage.getId() != null) {
            throw new BadRequestAlertException("A new stage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stage result = stageService.save(stage);
        return ResponseEntity.created(new URI("/api/stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stages} : Updates an existing stage.
     *
     * @param stage the stage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stage,
     * or with status {@code 400 (Bad Request)} if the stage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stages")
    public ResponseEntity<Stage> updateStage(@RequestBody Stage stage) throws URISyntaxException {
        log.debug("REST request to update Stage : {}", stage);
        if (stage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stage result = stageService.save(stage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stages} : get all the stages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stages in body.
     */
    @GetMapping("/stages")
    public List<Stage> getAllStages() {
        log.debug("REST request to get all Stages");
        return stageService.findAll();
    }

    /**
     * {@code GET  /stages/:id} : get the "id" stage.
     *
     * @param id the id of the stage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stages/{id}")
    public ResponseEntity<Stage> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        Optional<Stage> stage = stageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stage);
    }

    /**
     * {@code DELETE  /stages/:id} : delete the "id" stage.
     *
     * @param id the id of the stage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stages/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        log.debug("REST request to delete Stage : {}", id);
        stageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/stages?query=:query} : search for the stage corresponding
     * to the query.
     *
     * @param query the query of the stage search.
     * @return the result of the search.
     */
    @GetMapping("/_search/stages")
    public List<Stage> searchStages(@RequestParam String query) {
        log.debug("REST request to search Stages for query {}", query);
        return stageService.search(query);
    }
}
