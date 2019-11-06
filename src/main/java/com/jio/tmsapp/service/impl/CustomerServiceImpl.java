package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.CustomerService;
import com.jio.tmsapp.domain.Customer;
import com.jio.tmsapp.repository.CustomerRepository;
import com.jio.tmsapp.repository.search.CustomerSearchRepository;
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
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final CustomerSearchRepository customerSearchRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerSearchRepository customerSearchRepository) {
        this.customerRepository = customerRepository;
        this.customerSearchRepository = customerSearchRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        Customer result = customerRepository.save(customer);
        customerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll();
    }


    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
        customerSearchRepository.deleteById(id);
    }

    /**
     * Search for the customer corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Customer> search(String query) {
        log.debug("Request to search Customers for query {}", query);
        return StreamSupport
            .stream(customerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
