package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.InsuranceService;
import com.jio.tmsapp.domain.Insurance;
import com.jio.tmsapp.repository.InsuranceRepository;
import com.jio.tmsapp.repository.search.InsuranceSearchRepository;
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
 * Service Implementation for managing {@link Insurance}.
 */
@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceService {

    private final Logger log = LoggerFactory.getLogger(InsuranceServiceImpl.class);

    private final InsuranceRepository insuranceRepository;

    private final InsuranceSearchRepository insuranceSearchRepository;

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository, InsuranceSearchRepository insuranceSearchRepository) {
        this.insuranceRepository = insuranceRepository;
        this.insuranceSearchRepository = insuranceSearchRepository;
    }

    /**
     * Save a insurance.
     *
     * @param insurance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Insurance save(Insurance insurance) {
        log.debug("Request to save Insurance : {}", insurance);
        Insurance result = insuranceRepository.save(insurance);
        insuranceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the insurances.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Insurance> findAll() {
        log.debug("Request to get all Insurances");
        return insuranceRepository.findAll();
    }



    /**
    *  Get all the insurances where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Insurance> findAllWhereCustomerIsNull() {
        log.debug("Request to get all insurances where Customer is null");
        return StreamSupport
            .stream(insuranceRepository.findAll().spliterator(), false)
            .filter(insurance -> insurance.getCustomer() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one insurance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Insurance> findOne(Long id) {
        log.debug("Request to get Insurance : {}", id);
        return insuranceRepository.findById(id);
    }

    /**
     * Delete the insurance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insurance : {}", id);
        insuranceRepository.deleteById(id);
        insuranceSearchRepository.deleteById(id);
    }

    /**
     * Search for the insurance corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Insurance> search(String query) {
        log.debug("Request to search Insurances for query {}", query);
        return StreamSupport
            .stream(insuranceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
