package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.Biomarker;
import com.kognitic.trial.service.BiomarkerService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.Biomarker}.
 */
@RestController
@RequestMapping("/api")
public class BiomarkerResource {

    private final Logger log = LoggerFactory.getLogger(BiomarkerResource.class);

    private static final String ENTITY_NAME = "biomarker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiomarkerService biomarkerService;

    public BiomarkerResource(BiomarkerService biomarkerService) {
        this.biomarkerService = biomarkerService;
    }

    /**
     * {@code POST  /biomarkers} : Create a new biomarker.
     *
     * @param biomarker the biomarker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biomarker, or with status {@code 400 (Bad Request)} if the biomarker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biomarkers")
    public ResponseEntity<Biomarker> createBiomarker(@RequestBody Biomarker biomarker) throws URISyntaxException {
        log.debug("REST request to save Biomarker : {}", biomarker);
        if (biomarker.getId() != null) {
            throw new BadRequestAlertException("A new biomarker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Biomarker result = biomarkerService.save(biomarker);
        return ResponseEntity.created(new URI("/api/biomarkers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biomarkers} : Updates an existing biomarker.
     *
     * @param biomarker the biomarker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biomarker,
     * or with status {@code 400 (Bad Request)} if the biomarker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biomarker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biomarkers")
    public ResponseEntity<Biomarker> updateBiomarker(@RequestBody Biomarker biomarker) throws URISyntaxException {
        log.debug("REST request to update Biomarker : {}", biomarker);
        if (biomarker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Biomarker result = biomarkerService.save(biomarker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biomarker.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /biomarkers} : get all the biomarkers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of biomarkers in body.
     */
    @GetMapping("/biomarkers")
    public List<Biomarker> getAllBiomarkers() {
        log.debug("REST request to get all Biomarkers");
        return biomarkerService.findAll();
    }

    /**
     * {@code GET  /biomarkers/:id} : get the "id" biomarker.
     *
     * @param id the id of the biomarker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biomarker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biomarkers/{id}")
    public ResponseEntity<Biomarker> getBiomarker(@PathVariable Long id) {
        log.debug("REST request to get Biomarker : {}", id);
        Optional<Biomarker> biomarker = biomarkerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biomarker);
    }

    /**
     * {@code DELETE  /biomarkers/:id} : delete the "id" biomarker.
     *
     * @param id the id of the biomarker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/biomarkers/{id}")
    public ResponseEntity<Void> deleteBiomarker(@PathVariable Long id) {
        log.debug("REST request to delete Biomarker : {}", id);
        biomarkerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/biomarkers?query=:query} : search for the biomarker corresponding
     * to the query.
     *
     * @param query the query of the biomarker search.
     * @return the result of the search.
     */
    @GetMapping("/_search/biomarkers")
    public List<Biomarker> searchBiomarkers(@RequestParam String query) {
        log.debug("REST request to search Biomarkers for query {}", query);
        return biomarkerService.search(query);
    }
}
