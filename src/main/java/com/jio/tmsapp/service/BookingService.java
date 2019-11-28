package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.Booking;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Booking}.
 */
public interface BookingService {

    /**
     * Save a booking.
     *
     * @param booking the entity to save.
     * @return the persisted entity.
     */
    Booking save(Booking booking);

    /**
     * Get all the bookings.
     *
     * @return the list of entities.
     */
    List<Booking> findAll();


    /**
     * Get the "id" booking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Booking> findOne(Long id);

    /**
     * Delete the "id" booking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the booking corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Booking> search(String query);
}
