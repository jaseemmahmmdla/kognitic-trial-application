package com.kognitic.trial.service;

import com.kognitic.trial.domain.IndicationType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndicationType}.
 */
public interface IndicationTypeService {

    /**
     * Save a indicationType.
     *
     * @param indicationType the entity to save.
     * @return the persisted entity.
     */
    IndicationType save(IndicationType indicationType);

    /**
     * Get all the indicationTypes.
     *
     * @return the list of entities.
     */
    List<IndicationType> findAll();


    /**
     * Get the "id" indicationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndicationType> findOne(Long id);

    /**
     * Delete the "id" indicationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the indicationType corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<IndicationType> search(String query);
}
