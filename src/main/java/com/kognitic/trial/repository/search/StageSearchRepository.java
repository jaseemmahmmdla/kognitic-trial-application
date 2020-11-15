package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.Stage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Stage} entity.
 */
public interface StageSearchRepository extends ElasticsearchRepository<Stage, Long> {
}
