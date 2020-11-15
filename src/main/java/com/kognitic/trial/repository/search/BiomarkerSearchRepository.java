package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.Biomarker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Biomarker} entity.
 */
public interface BiomarkerSearchRepository extends ElasticsearchRepository<Biomarker, Long> {
}
