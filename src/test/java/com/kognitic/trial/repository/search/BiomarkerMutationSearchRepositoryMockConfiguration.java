package com.kognitic.trial.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link BiomarkerMutationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BiomarkerMutationSearchRepositoryMockConfiguration {

    @MockBean
    private BiomarkerMutationSearchRepository mockBiomarkerMutationSearchRepository;

}
