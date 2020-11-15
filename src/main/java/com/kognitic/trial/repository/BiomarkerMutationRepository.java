package com.kognitic.trial.repository;

import com.kognitic.trial.domain.BiomarkerMutation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BiomarkerMutation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiomarkerMutationRepository extends JpaRepository<BiomarkerMutation, Long> {
}
