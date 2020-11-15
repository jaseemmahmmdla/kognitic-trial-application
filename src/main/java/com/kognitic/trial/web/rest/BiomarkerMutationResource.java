package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.BiomarkerMutation;
import com.kognitic.trial.service.BiomarkerMutationService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.BiomarkerMutation}.
 */
@RestController
@RequestMapping("/api")
public class BiomarkerMutationResource {

    private final Logger log = LoggerFactory.getLogger(BiomarkerMutationResource.class);

    private static final String ENTITY_NAME = "biomarkerMutation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiomarkerMutationService biomarkerMutationService;

    public BiomarkerMutationResource(BiomarkerMutationService biomarkerMutationService) {
        this.biomarkerMutationService = biomarkerMutationService;
    }

    /**
     * {@code POST  /biomarker-mutations} : Create a new biomarkerMutation.
     *
     * @param biomarkerMutation the biomarkerMutation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biomarkerMutation, or with status {@code 400 (Bad Request)} if the biomarkerMutation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biomarker-mutations")
    public ResponseEntity<BiomarkerMutation> createBiomarkerMutation(@RequestBody BiomarkerMutation biomarkerMutation) throws URISyntaxException {
        log.debug("REST request to save BiomarkerMutation : {}", biomarkerMutation);
        if (biomarkerMutation.getId() != null) {
            throw new BadRequestAlertException("A new biomarkerMutation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiomarkerMutation result = biomarkerMutationService.save(biomarkerMutation);
        return ResponseEntity.created(new URI("/api/biomarker-mutations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biomarker-mutations} : Updates an existing biomarkerMutation.
     *
     * @param biomarkerMutation the biomarkerMutation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biomarkerMutation,
     * or with status {@code 400 (Bad Request)} if the biomarkerMutation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biomarkerMutation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biomarker-mutations")
    public ResponseEntity<BiomarkerMutation> updateBiomarkerMutation(@RequestBody BiomarkerMutation biomarkerMutation) throws URISyntaxException {
        log.debug("REST request to update BiomarkerMutation : {}", biomarkerMutation);
        if (biomarkerMutation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BiomarkerMutation result = biomarkerMutationService.save(biomarkerMutation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biomarkerMutation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /biomarker-mutations} : get all the biomarkerMutations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of biomarkerMutations in body.
     */
    @GetMapping("/biomarker-mutations")
    public List<BiomarkerMutation> getAllBiomarkerMutations() {
        log.debug("REST request to get all BiomarkerMutations");
        return biomarkerMutationService.findAll();
    }

    /**
     * {@code GET  /biomarker-mutations/:id} : get the "id" biomarkerMutation.
     *
     * @param id the id of the biomarkerMutation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biomarkerMutation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biomarker-mutations/{id}")
    public ResponseEntity<BiomarkerMutation> getBiomarkerMutation(@PathVariable Long id) {
        log.debug("REST request to get BiomarkerMutation : {}", id);
        Optional<BiomarkerMutation> biomarkerMutation = biomarkerMutationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biomarkerMutation);
    }

    /**
     * {@code DELETE  /biomarker-mutations/:id} : delete the "id" biomarkerMutation.
     *
     * @param id the id of the biomarkerMutation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/biomarker-mutations/{id}")
    public ResponseEntity<Void> deleteBiomarkerMutation(@PathVariable Long id) {
        log.debug("REST request to delete BiomarkerMutation : {}", id);
        biomarkerMutationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/biomarker-mutations?query=:query} : search for the biomarkerMutation corresponding
     * to the query.
     *
     * @param query the query of the biomarkerMutation search.
     * @return the result of the search.
     */
    @GetMapping("/_search/biomarker-mutations")
    public List<BiomarkerMutation> searchBiomarkerMutations(@RequestParam String query) {
        log.debug("REST request to search BiomarkerMutations for query {}", query);
        return biomarkerMutationService.search(query);
    }
}
