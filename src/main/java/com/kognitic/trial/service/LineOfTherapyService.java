package com.kognitic.trial.service;

import com.kognitic.trial.domain.LineOfTherapy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LineOfTherapy}.
 */
public interface LineOfTherapyService {

    /**
     * Save a lineOfTherapy.
     *
     * @param lineOfTherapy the entity to save.
     * @return the persisted entity.
     */
    LineOfTherapy save(LineOfTherapy lineOfTherapy);

    /**
     * Get all the lineOfTherapies.
     *
     * @return the list of entities.
     */
    List<LineOfTherapy> findAll();


    /**
     * Get the "id" lineOfTherapy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LineOfTherapy> findOne(Long id);

    /**
     * Delete the "id" lineOfTherapy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the lineOfTherapy corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LineOfTherapy> search(String query);
}
