package com.kognitic.trial.repository;

import com.kognitic.trial.domain.IndicationBucket;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IndicationBucket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicationBucketRepository extends JpaRepository<IndicationBucket, Long> {
}
