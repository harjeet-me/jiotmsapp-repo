package com.jio.tmsapp.service;

import com.jio.tmsapp.domain.Invoice;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Invoice}.
 */
public interface InvoiceService {

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    Invoice save(Invoice invoice);

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    List<Invoice> findAll();


    /**
     * Get the "id" invoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Invoice> findOne(Long id);

    /**
     * Delete the "id" invoice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the invoice corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Invoice> search(String query);
}
