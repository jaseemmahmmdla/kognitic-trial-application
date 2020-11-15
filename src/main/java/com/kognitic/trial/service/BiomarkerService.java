package com.kognitic.trial.service;

import com.kognitic.trial.domain.Biomarker;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Biomarker}.
 */
public interface BiomarkerService {

    /**
     * Save a biomarker.
     *
     * @param biomarker the entity to save.
     * @return the persisted entity.
     */
    Biomarker save(Biomarker biomarker);

    /**
     * Get all the biomarkers.
     *
     * @return the list of entities.
     */
    List<Biomarker> findAll();


    /**
     * Get the "id" biomarker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Biomarker> findOne(Long id);

    /**
     * Delete the "id" biomarker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the biomarker corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Biomarker> search(String query);
}
