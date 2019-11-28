package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.LoadOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link LoadOrder}.
 */
public interface LoadOrderService {

    /**
     * Save a loadOrder.
     *
     * @param loadOrder the entity to save.
     * @return the persisted entity.
     */
    LoadOrder save(LoadOrder loadOrder);

    /**
     * Get all the loadOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoadOrder> findAll(Pageable pageable);


    /**
     * Get the "id" loadOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoadOrder> findOne(Long id);

    /**
     * Delete the "id" loadOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the loadOrder corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoadOrder> search(String query, Pageable pageable);
}
