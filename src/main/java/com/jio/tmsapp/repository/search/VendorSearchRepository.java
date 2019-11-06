package com.jio.tmsapp.repository.search;
import com.jio.tmsapp.domain.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Vendor} entity.
 */
public interface VendorSearchRepository extends ElasticsearchRepository<Vendor, Long> {
}
