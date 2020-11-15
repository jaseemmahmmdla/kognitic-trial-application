package com.kognitic.trial.service;

import com.kognitic.trial.domain.BiomarkerMutation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BiomarkerMutation}.
 */
public interface BiomarkerMutationService {

    /**
     * Save a biomarkerMutation.
     *
     * @param biomarkerMutation the entity to save.
     * @return the persisted entity.
     */
    BiomarkerMutation save(BiomarkerMutation biomarkerMutation);

    /**
     * Get all the biomarkerMutations.
     *
     * @return the list of entities.
     */
    List<BiomarkerMutation> findAll();


    /**
     * Get the "id" biomarkerMutation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BiomarkerMutation> findOne(Long id);

    /**
     * Delete the "id" biomarkerMutation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the biomarkerMutation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<BiomarkerMutation> search(String query);
}
