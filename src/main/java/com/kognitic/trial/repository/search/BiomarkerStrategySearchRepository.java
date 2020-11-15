package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.BiomarkerStrategy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BiomarkerStrategy} entity.
 */
public interface BiomarkerStrategySearchRepository extends ElasticsearchRepository<BiomarkerStrategy, Long> {
}
