package com.kognitic.trial.service;

import com.kognitic.trial.domain.Trial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Trial}.
 */
public interface TrialService {

    /**
     * Save a trial.
     *
     * @param trial the entity to save.
     * @return the persisted entity.
     */
    Trial save(Trial trial);

    /**
     * Get all the trials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trial> findAll(Pageable pageable);


    /**
     * Get the "id" trial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trial> findOne(Long id);

    /**
     * Delete the "id" trial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the trial corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trial> search(String query, Pageable pageable);
}
