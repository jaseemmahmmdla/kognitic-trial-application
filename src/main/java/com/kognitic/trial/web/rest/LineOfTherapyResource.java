package com.kognitic.trial.web.rest;

import com.kognitic.trial.domain.LineOfTherapy;
import com.kognitic.trial.service.LineOfTherapyService;
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
 * REST controller for managing {@link com.kognitic.trial.domain.LineOfTherapy}.
 */
@RestController
@RequestMapping("/api")
public class LineOfTherapyResource {

    private final Logger log = LoggerFactory.getLogger(LineOfTherapyResource.class);

    private static final String ENTITY_NAME = "lineOfTherapy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineOfTherapyService lineOfTherapyService;

    public LineOfTherapyResource(LineOfTherapyService lineOfTherapyService) {
        this.lineOfTherapyService = lineOfTherapyService;
    }

    /**
     * {@code POST  /line-of-therapies} : Create a new lineOfTherapy.
     *
     * @param lineOfTherapy the lineOfTherapy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineOfTherapy, or with status {@code 400 (Bad Request)} if the lineOfTherapy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/line-of-therapies")
    public ResponseEntity<LineOfTherapy> createLineOfTherapy(@RequestBody LineOfTherapy lineOfTherapy) throws URISyntaxException {
        log.debug("REST request to save LineOfTherapy : {}", lineOfTherapy);
        if (lineOfTherapy.getId() != null) {
            throw new BadRequestAlertException("A new lineOfTherapy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineOfTherapy result = lineOfTherapyService.save(lineOfTherapy);
        return ResponseEntity.created(new URI("/api/line-of-therapies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /line-of-therapies} : Updates an existing lineOfTherapy.
     *
     * @param lineOfTherapy the lineOfTherapy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineOfTherapy,
     * or with status {@code 400 (Bad Request)} if the lineOfTherapy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineOfTherapy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/line-of-therapies")
    public ResponseEntity<LineOfTherapy> updateLineOfTherapy(@RequestBody LineOfTherapy lineOfTherapy) throws URISyntaxException {
        log.debug("REST request to update LineOfTherapy : {}", lineOfTherapy);
        if (lineOfTherapy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LineOfTherapy result = lineOfTherapyService.save(lineOfTherapy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lineOfTherapy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /line-of-therapies} : get all the lineOfTherapies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lineOfTherapies in body.
     */
    @GetMapping("/line-of-therapies")
    public List<LineOfTherapy> getAllLineOfTherapies() {
        log.debug("REST request to get all LineOfTherapies");
        return lineOfTherapyService.findAll();
    }

    /**
     * {@code GET  /line-of-therapies/:id} : get the "id" lineOfTherapy.
     *
     * @param id the id of the lineOfTherapy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineOfTherapy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/line-of-therapies/{id}")
    public ResponseEntity<LineOfTherapy> getLineOfTherapy(@PathVariable Long id) {
        log.debug("REST request to get LineOfTherapy : {}", id);
        Optional<LineOfTherapy> lineOfTherapy = lineOfTherapyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lineOfTherapy);
    }

    /**
     * {@code DELETE  /line-of-therapies/:id} : delete the "id" lineOfTherapy.
     *
     * @param id the id of the lineOfTherapy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/line-of-therapies/{id}")
    public ResponseEntity<Void> deleteLineOfTherapy(@PathVariable Long id) {
        log.debug("REST request to delete LineOfTherapy : {}", id);
        lineOfTherapyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/line-of-therapies?query=:query} : search for the lineOfTherapy corresponding
     * to the query.
     *
     * @param query the query of the lineOfTherapy search.
     * @return the result of the search.
     */
    @GetMapping("/_search/line-of-therapies")
    public List<LineOfTherapy> searchLineOfTherapies(@RequestParam String query) {
        log.debug("REST request to search LineOfTherapies for query {}", query);
        return lineOfTherapyService.search(query);
    }
}
