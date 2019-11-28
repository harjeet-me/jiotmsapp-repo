package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.LoadOrderService;
import com.jio.tmsapp.domain.LoadOrder;
import com.jio.tmsapp.repository.LoadOrderRepository;
import com.jio.tmsapp.repository.search.LoadOrderSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LoadOrder}.
 */
@Service
@Transactional
public class LoadOrderServiceImpl implements LoadOrderService {

    private final Logger log = LoggerFactory.getLogger(LoadOrderServiceImpl.class);

    private final LoadOrderRepository loadOrderRepository;

    private final LoadOrderSearchRepository loadOrderSearchRepository;

    public LoadOrderServiceImpl(LoadOrderRepository loadOrderRepository, LoadOrderSearchRepository loadOrderSearchRepository) {
        this.loadOrderRepository = loadOrderRepository;
        this.loadOrderSearchRepository = loadOrderSearchRepository;
    }

    /**
     * Save a loadOrder.
     *
     * @param loadOrder the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LoadOrder save(LoadOrder loadOrder) {
        log.debug("Request to save LoadOrder : {}", loadOrder);
        LoadOrder result = loadOrderRepository.save(loadOrder);
        loadOrderSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the loadOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoadOrder> findAll(Pageable pageable) {
        log.debug("Request to get all LoadOrders");
        return loadOrderRepository.findAll(pageable);
    }


    /**
     * Get one loadOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LoadOrder> findOne(Long id) {
        log.debug("Request to get LoadOrder : {}", id);
        return loadOrderRepository.findById(id);
    }

    /**
     * Delete the loadOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoadOrder : {}", id);
        loadOrderRepository.deleteById(id);
        loadOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the loadOrder corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoadOrder> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LoadOrders for query {}", query);
        return loadOrderSearchRepository.search(queryStringQuery(query), pageable);    }
}
