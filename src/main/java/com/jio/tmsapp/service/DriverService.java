package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.Driver;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Driver}.
 */
public interface DriverService {

    /**
     * Save a driver.
     *
     * @param driver the entity to save.
     * @return the persisted entity.
     */
    Driver save(Driver driver);

    /**
     * Get all the drivers.
     *
     * @return the list of entities.
     */
    List<Driver> findAll();


    /**
     * Get the "id" driver.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Driver> findOne(Long id);

    /**
     * Delete the "id" driver.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the driver corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Driver> search(String query);
}
