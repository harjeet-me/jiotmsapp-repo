package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.BookingService;
import com.jio.tmsapp.domain.Booking;
import com.jio.tmsapp.repository.BookingRepository;
import com.jio.tmsapp.repository.search.BookingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    private final BookingSearchRepository bookingSearchRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingSearchRepository bookingSearchRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSearchRepository = bookingSearchRepository;
    }

    /**
     * Save a booking.
     *
     * @param booking the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        Booking result = bookingRepository.save(booking);
        bookingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bookings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Booking> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll(pageable);
    }


    /**
     * Get one booking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id);
    }

    /**
     * Delete the booking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
        bookingSearchRepository.deleteById(id);
    }

    /**
     * Search for the booking corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Booking> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bookings for query {}", query);
        return bookingSearchRepository.search(queryStringQuery(query), pageable);    }
}
