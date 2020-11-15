package com.kognitic.trial.service;

import com.kognitic.trial.domain.BiomarkerStrategy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BiomarkerStrategy}.
 */
public interface BiomarkerStrategyService {

    /**
     * Save a biomarkerStrategy.
     *
     * @param biomarkerStrategy the entity to save.
     * @return the persisted entity.
     */
    BiomarkerStrategy save(BiomarkerStrategy biomarkerStrategy);

    /**
     * Get all the biomarkerStrategies.
     *
     * @return the list of entities.
     */
    List<BiomarkerStrategy> findAll();


    /**
     * Get the "id" biomarkerStrategy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BiomarkerStrategy> findOne(Long id);

    /**
     * Delete the "id" biomarkerStrategy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the biomarkerStrategy corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<BiomarkerStrategy> search(String query);
}
