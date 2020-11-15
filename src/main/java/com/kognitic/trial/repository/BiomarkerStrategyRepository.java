package com.kognitic.trial.repository;

import com.kognitic.trial.domain.BiomarkerStrategy;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BiomarkerStrategy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiomarkerStrategyRepository extends JpaRepository<BiomarkerStrategy, Long> {
}
