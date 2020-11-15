package com.kognitic.trial.service;

import com.kognitic.trial.domain.Indication;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Indication}.
 */
public interface IndicationService {

    /**
     * Save a indication.
     *
     * @param indication the entity to save.
     * @return the persisted entity.
     */
    Indication save(Indication indication);

    /**
     * Get all the indications.
     *
     * @return the list of entities.
     */
    List<Indication> findAll();


    /**
     * Get the "id" indication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Indication> findOne(Long id);

    /**
     * Delete the "id" indication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the indication corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Indication> search(String query);
}
