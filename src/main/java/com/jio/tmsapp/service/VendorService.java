package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.Vendor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Vendor}.
 */
public interface VendorService {

    /**
     * Save a vendor.
     *
     * @param vendor the entity to save.
     * @return the persisted entity.
     */
    Vendor save(Vendor vendor);

    /**
     * Get all the vendors.
     *
     * @return the list of entities.
     */
    List<Vendor> findAll();


    /**
     * Get the "id" vendor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vendor> findOne(Long id);

    /**
     * Delete the "id" vendor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the vendor corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Vendor> search(String query);
}
