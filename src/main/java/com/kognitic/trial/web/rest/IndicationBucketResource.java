package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.IndicationBucket;
import com.kognitic.trial.service.IndicationBucketService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.IndicationBucket}.
 */
@RestController
@RequestMapping("/api")
public class IndicationBucketResource {

    private final Logger log = LoggerFactory.getLogger(IndicationBucketResource.class);

    private static final String ENTITY_NAME = "indicationBucket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicationBucketService indicationBucketService;

    public IndicationBucketResource(IndicationBucketService indicationBucketService) {
        this.indicationBucketService = indicationBucketService;
    }

    /**
     * {@code POST  /indication-buckets} : Create a new indicationBucket.
     *
     * @param indicationBucket the indicationBucket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicationBucket, or with status {@code 400 (Bad Request)} if the indicationBucket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indication-buckets")
    public ResponseEntity<IndicationBucket> createIndicationBucket(@RequestBody IndicationBucket indicationBucket) throws URISyntaxException {
        log.debug("REST request to save IndicationBucket : {}", indicationBucket);
        if (indicationBucket.getId() != null) {
            throw new BadRequestAlertException("A new indicationBucket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndicationBucket result = indicationBucketService.save(indicationBucket);
        return ResponseEntity.created(new URI("/api/indication-buckets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indication-buckets} : Updates an existing indicationBucket.
     *
     * @param indicationBucket the indicationBucket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicationBucket,
     * or with status {@code 400 (Bad Request)} if the indicationBucket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicationBucket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indication-buckets")
    public ResponseEntity<IndicationBucket> updateIndicationBucket(@RequestBody IndicationBucket indicationBucket) throws URISyntaxException {
        log.debug("REST request to update IndicationBucket : {}", indicationBucket);
        if (indicationBucket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndicationBucket result = indicationBucketService.save(indicationBucket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicationBucket.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /indication-buckets} : get all the indicationBuckets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicationBuckets in body.
     */
    @GetMapping("/indication-buckets")
    public List<IndicationBucket> getAllIndicationBuckets() {
        log.debug("REST request to get all IndicationBuckets");
        return indicationBucketService.findAll();
    }

    /**
     * {@code GET  /indication-buckets/:id} : get the "id" indicationBucket.
     *
     * @param id the id of the indicationBucket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicationBucket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indication-buckets/{id}")
    public ResponseEntity<IndicationBucket> getIndicationBucket(@PathVariable Long id) {
        log.debug("REST request to get IndicationBucket : {}", id);
        Optional<IndicationBucket> indicationBucket = indicationBucketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicationBucket);
    }

    /**
     * {@code DELETE  /indication-buckets/:id} : delete the "id" indicationBucket.
     *
     * @param id the id of the indicationBucket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indication-buckets/{id}")
    public ResponseEntity<Void> deleteIndicationBucket(@PathVariable Long id) {
        log.debug("REST request to delete IndicationBucket : {}", id);
        indicationBucketService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/indication-buckets?query=:query} : search for the indicationBucket corresponding
     * to the query.
     *
     * @param query the query of the indicationBucket search.
     * @return the result of the search.
     */
    @GetMapping("/_search/indication-buckets")
    public List<IndicationBucket> searchIndicationBuckets(@RequestParam String query) {
        log.debug("REST request to search IndicationBuckets for query {}", query);
        return indicationBucketService.search(query);
    }
}
