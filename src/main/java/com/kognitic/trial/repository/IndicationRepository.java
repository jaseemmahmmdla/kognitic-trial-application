package com.kognitic.trial.repository;

import com.kognitic.trial.domain.Indication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Indication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicationRepository extends JpaRepository<Indication, Long> {
}
