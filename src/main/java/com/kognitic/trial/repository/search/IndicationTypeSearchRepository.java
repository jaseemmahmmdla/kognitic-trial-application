package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.IndicationType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link IndicationType} entity.
 */
public interface IndicationTypeSearchRepository extends ElasticsearchRepository<IndicationType, Long> {
}
