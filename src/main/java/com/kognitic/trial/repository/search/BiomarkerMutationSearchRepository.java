package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.BiomarkerMutation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BiomarkerMutation} entity.
 */
public interface BiomarkerMutationSearchRepository extends ElasticsearchRepository<BiomarkerMutation, Long> {
}
