package com.kognitic.trial.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link IndicationBucketSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class IndicationBucketSearchRepositoryMockConfiguration {

    @MockBean
    private IndicationBucketSearchRepository mockIndicationBucketSearchRepository;

}
