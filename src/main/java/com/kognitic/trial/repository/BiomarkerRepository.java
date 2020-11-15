package com.kognitic.trial.repository;

import com.kognitic.trial.domain.Biomarker;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Biomarker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiomarkerRepository extends JpaRepository<Biomarker, Long> {
}
