package com.jio.tmsapp.repository.search;
import com.jio.tmsapp.domain.LoadOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LoadOrder} entity.
 */
public interface LoadOrderSearchRepository extends ElasticsearchRepository<LoadOrder, Long> {
}
