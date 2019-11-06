package com.jio.tmsapp.repository.search;
import com.jio.tmsapp.domain.Container;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Container} entity.
 */
public interface ContainerSearchRepository extends ElasticsearchRepository<Container, Long> {
}
