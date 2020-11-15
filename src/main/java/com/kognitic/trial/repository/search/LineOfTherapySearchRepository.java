package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.LineOfTherapy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link LineOfTherapy} entity.
 */
public interface LineOfTherapySearchRepository extends ElasticsearchRepository<LineOfTherapy, Long> {
}
