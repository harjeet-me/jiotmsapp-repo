package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.BookingItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BookingItem}.
 */
public interface BookingItemService {

    /**
     * Save a bookingItem.
     *
     * @param bookingItem the entity to save.
     * @return the persisted entity.
     */
    BookingItem save(BookingItem bookingItem);

    /**
     * Get all the bookingItems.
     *
     * @return the list of entities.
     */
    List<BookingItem> findAll();


    /**
     * Get the "id" bookingItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookingItem> findOne(Long id);

    /**
     * Delete the "id" bookingItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the bookingItem corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<BookingItem> search(String query);
}
