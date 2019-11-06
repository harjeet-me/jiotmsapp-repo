package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.InvoiceItemService;
import com.jio.tmsapp.domain.InvoiceItem;
import com.jio.tmsapp.repository.InvoiceItemRepository;
import com.jio.tmsapp.repository.search.InvoiceItemSearchRepository;
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
 * Service Implementation for managing {@link InvoiceItem}.
 */
@Service
@Transactional
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemServiceImpl.class);

    private final InvoiceItemRepository invoiceItemRepository;

    private final InvoiceItemSearchRepository invoiceItemSearchRepository;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository, InvoiceItemSearchRepository invoiceItemSearchRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceItemSearchRepository = invoiceItemSearchRepository;
    }

    /**
     * Save a invoiceItem.
     *
     * @param invoiceItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceItem save(InvoiceItem invoiceItem) {
        log.debug("Request to save InvoiceItem : {}", invoiceItem);
        InvoiceItem result = invoiceItemRepository.save(invoiceItem);
        invoiceItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the invoiceItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceItem> findAll() {
        log.debug("Request to get all InvoiceItems");
        return invoiceItemRepository.findAll();
    }


    /**
     * Get one invoiceItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceItem> findOne(Long id) {
        log.debug("Request to get InvoiceItem : {}", id);
        return invoiceItemRepository.findById(id);
    }

    /**
     * Delete the invoiceItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceItem : {}", id);
        invoiceItemRepository.deleteById(id);
        invoiceItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoiceItem corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceItem> search(String query) {
        log.debug("Request to search InvoiceItems for query {}", query);
        return StreamSupport
            .stream(invoiceItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
