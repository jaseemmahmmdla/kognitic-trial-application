package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.Indication;
import com.kognitic.trial.service.IndicationService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.Indication}.
 */
@RestController
@RequestMapping("/api")
public class IndicationResource {

    private final Logger log = LoggerFactory.getLogger(IndicationResource.class);

    private static final String ENTITY_NAME = "indication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicationService indicationService;

    public IndicationResource(IndicationService indicationService) {
        this.indicationService = indicationService;
    }

    /**
     * {@code POST  /indications} : Create a new indication.
     *
     * @param indication the indication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indication, or with status {@code 400 (Bad Request)} if the indication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indications")
    public ResponseEntity<Indication> createIndication(@RequestBody Indication indication) throws URISyntaxException {
        log.debug("REST request to save Indication : {}", indication);
        if (indication.getId() != null) {
            throw new BadRequestAlertException("A new indication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Indication result = indicationService.save(indication);
        return ResponseEntity.created(new URI("/api/indications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indications} : Updates an existing indication.
     *
     * @param indication the indication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indication,
     * or with status {@code 400 (Bad Request)} if the indication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indications")
    public ResponseEntity<Indication> updateIndication(@RequestBody Indication indication) throws URISyntaxException {
        log.debug("REST request to update Indication : {}", indication);
        if (indication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Indication result = indicationService.save(indication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indication.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /indications} : get all the indications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indications in body.
     */
    @GetMapping("/indications")
    public List<Indication> getAllIndications() {
        log.debug("REST request to get all Indications");
        return indicationService.findAll();
    }

    /**
     * {@code GET  /indications/:id} : get the "id" indication.
     *
     * @param id the id of the indication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indications/{id}")
    public ResponseEntity<Indication> getIndication(@PathVariable Long id) {
        log.debug("REST request to get Indication : {}", id);
        Optional<Indication> indication = indicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indication);
    }

    /**
     * {@code DELETE  /indications/:id} : delete the "id" indication.
     *
     * @param id the id of the indication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indications/{id}")
    public ResponseEntity<Void> deleteIndication(@PathVariable Long id) {
        log.debug("REST request to delete Indication : {}", id);
        indicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/indications?query=:query} : search for the indication corresponding
     * to the query.
     *
     * @param query the query of the indication search.
     * @return the result of the search.
     */
    @GetMapping("/_search/indications")
    public List<Indication> searchIndications(@RequestParam String query) {
        log.debug("REST request to search Indications for query {}", query);
        return indicationService.search(query);
    }
}
