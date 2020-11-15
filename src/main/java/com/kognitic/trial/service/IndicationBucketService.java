package com.kognitic.trial.service;

import com.kognitic.trial.domain.IndicationBucket;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndicationBucket}.
 */
public interface IndicationBucketService {

    /**
     * Save a indicationBucket.
     *
     * @param indicationBucket the entity to save.
     * @return the persisted entity.
     */
    IndicationBucket save(IndicationBucket indicationBucket);

    /**
     * Get all the indicationBuckets.
     *
     * @return the list of entities.
     */
    List<IndicationBucket> findAll();


    /**
     * Get the "id" indicationBucket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndicationBucket> findOne(Long id);

    /**
     * Delete the "id" indicationBucket.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the indicationBucket corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<IndicationBucket> search(String query);
}
