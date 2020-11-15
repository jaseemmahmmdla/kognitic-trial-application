package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.Indication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Indication} entity.
 */
public interface IndicationSearchRepository extends ElasticsearchRepository<Indication, Long> {
}
