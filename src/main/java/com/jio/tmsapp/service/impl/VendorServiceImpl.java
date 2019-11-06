package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.VendorService;
import com.jio.tmsapp.domain.Vendor;
import com.jio.tmsapp.repository.VendorRepository;
import com.jio.tmsapp.repository.search.VendorSearchRepository;
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
 * Service Implementation for managing {@link Vendor}.
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    private final VendorSearchRepository vendorSearchRepository;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorSearchRepository vendorSearchRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorSearchRepository = vendorSearchRepository;
    }

    /**
     * Save a vendor.
     *
     * @param vendor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Vendor save(Vendor vendor) {
        log.debug("Request to save Vendor : {}", vendor);
        Vendor result = vendorRepository.save(vendor);
        vendorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the vendors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vendor> findAll() {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll();
    }


    /**
     * Get one vendor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vendor> findOne(Long id) {
        log.debug("Request to get Vendor : {}", id);
        return vendorRepository.findById(id);
    }

    /**
     * Delete the vendor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.deleteById(id);
        vendorSearchRepository.deleteById(id);
    }

    /**
     * Search for the vendor corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vendor> search(String query) {
        log.debug("Request to search Vendors for query {}", query);
        return StreamSupport
            .stream(vendorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
