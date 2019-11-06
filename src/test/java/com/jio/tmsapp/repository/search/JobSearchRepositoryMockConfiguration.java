package com.jio.tmsapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link JobSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class JobSearchRepositoryMockConfiguration {

    @MockBean
    private JobSearchRepository mockJobSearchRepository;

}
