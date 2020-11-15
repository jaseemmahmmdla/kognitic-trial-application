package com.kognitic.trial.repository;

import com.kognitic.trial.domain.IndicationType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IndicationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicationTypeRepository extends JpaRepository<IndicationType, Long> {
}
