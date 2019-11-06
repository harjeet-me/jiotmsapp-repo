package com.jio.tmsapp.repository.search;
import com.jio.tmsapp.domain.Booking;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Booking} entity.
 */
public interface BookingSearchRepository extends ElasticsearchRepository<Booking, Long> {
}
