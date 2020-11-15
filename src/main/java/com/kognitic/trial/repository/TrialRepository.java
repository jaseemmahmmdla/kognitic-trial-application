package com.kognitic.trial.repository;

import com.kognitic.trial.domain.Trial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Trial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrialRepository extends JpaRepository<Trial, Long> {
}
