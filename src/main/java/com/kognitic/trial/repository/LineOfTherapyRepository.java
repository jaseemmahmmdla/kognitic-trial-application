package com.kognitic.trial.repository;

import com.kognitic.trial.domain.LineOfTherapy;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LineOfTherapy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineOfTherapyRepository extends JpaRepository<LineOfTherapy, Long> {
}
