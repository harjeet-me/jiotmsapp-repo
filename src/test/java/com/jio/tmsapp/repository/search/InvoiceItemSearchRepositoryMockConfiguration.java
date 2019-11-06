package com.jio.tmsapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InvoiceItemSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InvoiceItemSearchRepositoryMockConfiguration {

    @MockBean
    private InvoiceItemSearchRepository mockInvoiceItemSearchRepository;

}
