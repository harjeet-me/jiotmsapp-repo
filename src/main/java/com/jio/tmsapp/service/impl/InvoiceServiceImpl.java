package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.InvoiceService;
import com.jio.tmsapp.domain.Invoice;
import com.jio.tmsapp.repository.InvoiceRepository;
import com.jio.tmsapp.repository.search.InvoiceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceSearchRepository invoiceSearchRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceSearchRepository invoiceSearchRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceSearchRepository = invoiceSearchRepository;
    }

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        Invoice result = invoiceRepository.save(invoice);
        invoiceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll();
    }


    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
        invoiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoice corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> search(String query) {
        log.debug("Request to search Invoices for query {}", query);
        return StreamSupport
            .stream(invoiceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
