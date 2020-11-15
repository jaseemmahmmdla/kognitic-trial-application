package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.Trial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Trial} entity.
 */
public interface TrialSearchRepository extends ElasticsearchRepository<Trial, Long> {
}
