package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.Trial;
import com.kognitic.trial.service.TrialService;
import com.kognitic.trial.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.kognitic.trial.domain.Trial}.
 */
@RestController
@RequestMapping("/api")
public class TrialResource {

    private final Logger log = LoggerFactory.getLogger(TrialResource.class);

    private static final String ENTITY_NAME = "trial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrialService trialService;

    public TrialResource(TrialService trialService) {
        this.trialService = trialService;
    }

    /**
     * {@code POST  /trials} : Create a new trial.
     *
     * @param trial the trial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trial, or with status {@code 400 (Bad Request)} if the trial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trials")
    public ResponseEntity<Trial> createTrial(@RequestBody Trial trial) throws URISyntaxException {
        log.debug("REST request to save Trial : {}", trial);
        if (trial.getId() != null) {
            throw new BadRequestAlertException("A new trial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trial result = trialService.save(trial);
        return ResponseEntity.created(new URI("/api/trials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trials} : Updates an existing trial.
     *
     * @param trial the trial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trial,
     * or with status {@code 400 (Bad Request)} if the trial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trials")
    public ResponseEntity<Trial> updateTrial(@RequestBody Trial trial) throws URISyntaxException {
        log.debug("REST request to update Trial : {}", trial);
        if (trial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trial result = trialService.save(trial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trials} : get all the trials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trials in body.
     */
    @GetMapping("/trials")
    public ResponseEntity<List<Trial>> getAllTrials(Pageable pageable) {
        log.debug("REST request to get a page of Trials");
        Page<Trial> page = trialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trials/:id} : get the "id" trial.
     *
     * @param id the id of the trial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trials/{id}")
    public ResponseEntity<Trial> getTrial(@PathVariable Long id) {
        log.debug("REST request to get Trial : {}", id);
        Optional<Trial> trial = trialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trial);
    }

    /**
     * {@code DELETE  /trials/:id} : delete the "id" trial.
     *
     * @param id the id of the trial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trials/{id}")
    public ResponseEntity<Void> deleteTrial(@PathVariable Long id) {
        log.debug("REST request to delete Trial : {}", id);
        trialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/trials?query=:query} : search for the trial corresponding
     * to the query.
     *
     * @param query the query of the trial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/trials")
    public ResponseEntity<List<Trial>> searchTrials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Trials for query {}", query);
        Page<Trial> page = trialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
