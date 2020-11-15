package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.IndicationType;
import com.kognitic.trial.service.IndicationTypeService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.IndicationType}.
 */
@RestController
@RequestMapping("/api")
public class IndicationTypeResource {

    private final Logger log = LoggerFactory.getLogger(IndicationTypeResource.class);

    private static final String ENTITY_NAME = "indicationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicationTypeService indicationTypeService;

    public IndicationTypeResource(IndicationTypeService indicationTypeService) {
        this.indicationTypeService = indicationTypeService;
    }

    /**
     * {@code POST  /indication-types} : Create a new indicationType.
     *
     * @param indicationType the indicationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicationType, or with status {@code 400 (Bad Request)} if the indicationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indication-types")
    public ResponseEntity<IndicationType> createIndicationType(@RequestBody IndicationType indicationType) throws URISyntaxException {
        log.debug("REST request to save IndicationType : {}", indicationType);
        if (indicationType.getId() != null) {
            throw new BadRequestAlertException("A new indicationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndicationType result = indicationTypeService.save(indicationType);
        return ResponseEntity.created(new URI("/api/indication-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indication-types} : Updates an existing indicationType.
     *
     * @param indicationType the indicationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicationType,
     * or with status {@code 400 (Bad Request)} if the indicationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indication-types")
    public ResponseEntity<IndicationType> updateIndicationType(@RequestBody IndicationType indicationType) throws URISyntaxException {
        log.debug("REST request to update IndicationType : {}", indicationType);
        if (indicationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndicationType result = indicationTypeService.save(indicationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /indication-types} : get all the indicationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicationTypes in body.
     */
    @GetMapping("/indication-types")
    public List<IndicationType> getAllIndicationTypes() {
        log.debug("REST request to get all IndicationTypes");
        return indicationTypeService.findAll();
    }

    /**
     * {@code GET  /indication-types/:id} : get the "id" indicationType.
     *
     * @param id the id of the indicationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indication-types/{id}")
    public ResponseEntity<IndicationType> getIndicationType(@PathVariable Long id) {
        log.debug("REST request to get IndicationType : {}", id);
        Optional<IndicationType> indicationType = indicationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicationType);
    }

    /**
     * {@code DELETE  /indication-types/:id} : delete the "id" indicationType.
     *
     * @param id the id of the indicationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indication-types/{id}")
    public ResponseEntity<Void> deleteIndicationType(@PathVariable Long id) {
        log.debug("REST request to delete IndicationType : {}", id);
        indicationTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/indication-types?query=:query} : search for the indicationType corresponding
     * to the query.
     *
     * @param query the query of the indicationType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/indication-types")
    public List<IndicationType> searchIndicationTypes(@RequestParam String query) {
        log.debug("REST request to search IndicationTypes for query {}", query);
        return indicationTypeService.search(query);
    }
}
