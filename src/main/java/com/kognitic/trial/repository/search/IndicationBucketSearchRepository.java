package com.kognitic.trial.repository.search;

import com.kognitic.trial.domain.IndicationBucket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link IndicationBucket} entity.
 */
public interface IndicationBucketSearchRepository extends ElasticsearchRepository<IndicationBucket, Long> {
}
