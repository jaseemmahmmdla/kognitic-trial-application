package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.BiomarkerStrategy;
import com.kognitic.trial.service.BiomarkerStrategyService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.BiomarkerStrategy}.
 */
@RestController
@RequestMapping("/api")
public class BiomarkerStrategyResource {

    private final Logger log = LoggerFactory.getLogger(BiomarkerStrategyResource.class);

    private static final String ENTITY_NAME = "biomarkerStrategy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiomarkerStrategyService biomarkerStrategyService;

    public BiomarkerStrategyResource(BiomarkerStrategyService biomarkerStrategyService) {
        this.biomarkerStrategyService = biomarkerStrategyService;
    }

    /**
     * {@code POST  /biomarker-strategies} : Create a new biomarkerStrategy.
     *
     * @param biomarkerStrategy the biomarkerStrategy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biomarkerStrategy, or with status {@code 400 (Bad Request)} if the biomarkerStrategy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biomarker-strategies")
    public ResponseEntity<BiomarkerStrategy> createBiomarkerStrategy(@RequestBody BiomarkerStrategy biomarkerStrategy) throws URISyntaxException {
        log.debug("REST request to save BiomarkerStrategy : {}", biomarkerStrategy);
        if (biomarkerStrategy.getId() != null) {
            throw new BadRequestAlertException("A new biomarkerStrategy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiomarkerStrategy result = biomarkerStrategyService.save(biomarkerStrategy);
        return ResponseEntity.created(new URI("/api/biomarker-strategies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biomarker-strategies} : Updates an existing biomarkerStrategy.
     *
     * @param biomarkerStrategy the biomarkerStrategy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biomarkerStrategy,
     * or with status {@code 400 (Bad Request)} if the biomarkerStrategy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biomarkerStrategy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biomarker-strategies")
    public ResponseEntity<BiomarkerStrategy> updateBiomarkerStrategy(@RequestBody BiomarkerStrategy biomarkerStrategy) throws URISyntaxException {
        log.debug("REST request to update BiomarkerStrategy : {}", biomarkerStrategy);
        if (biomarkerStrategy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BiomarkerStrategy result = biomarkerStrategyService.save(biomarkerStrategy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biomarkerStrategy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /biomarker-strategies} : get all the biomarkerStrategies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of biomarkerStrategies in body.
     */
    @GetMapping("/biomarker-strategies")
    public List<BiomarkerStrategy> getAllBiomarkerStrategies() {
        log.debug("REST request to get all BiomarkerStrategies");
        return biomarkerStrategyService.findAll();
    }

    /**
     * {@code GET  /biomarker-strategies/:id} : get the "id" biomarkerStrategy.
     *
     * @param id the id of the biomarkerStrategy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biomarkerStrategy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biomarker-strategies/{id}")
    public ResponseEntity<BiomarkerStrategy> getBiomarkerStrategy(@PathVariable Long id) {
        log.debug("REST request to get BiomarkerStrategy : {}", id);
        Optional<BiomarkerStrategy> biomarkerStrategy = biomarkerStrategyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biomarkerStrategy);
    }

    /**
     * {@code DELETE  /biomarker-strategies/:id} : delete the "id" biomarkerStrategy.
     *
     * @param id the id of the biomarkerStrategy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/biomarker-strategies/{id}")
    public ResponseEntity<Void> deleteBiomarkerStrategy(@PathVariable Long id) {
        log.debug("REST request to delete BiomarkerStrategy : {}", id);
        biomarkerStrategyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/biomarker-strategies?query=:query} : search for the biomarkerStrategy corresponding
     * to the query.
     *
     * @param query the query of the biomarkerStrategy search.
     * @return the result of the search.
     */
    @GetMapping("/_search/biomarker-strategies")
    public List<BiomarkerStrategy> searchBiomarkerStrategies(@RequestParam String query) {
        log.debug("REST request to search BiomarkerStrategies for query {}", query);
        return biomarkerStrategyService.search(query);
    }
}
