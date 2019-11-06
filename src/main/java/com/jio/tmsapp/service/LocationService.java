package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.Location;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Location}.
 */
public interface LocationService {

    /**
     * Save a location.
     *
     * @param location the entity to save.
     * @return the persisted entity.
     */
    Location save(Location location);

    /**
     * Get all the locations.
     *
     * @return the list of entities.
     */
    List<Location> findAll();
    /**
     * Get all the LocationDTO where Customer is {@code null}.
     *
     * @return the list of entities.
     */
    List<Location> findAllWhereCustomerIsNull();


    /**
     * Get the "id" location.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Location> findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the location corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Location> search(String query);
}
