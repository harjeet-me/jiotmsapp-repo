package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.ContainerService;
import com.jio.tmsapp.domain.Container;
import com.jio.tmsapp.repository.ContainerRepository;
import com.jio.tmsapp.repository.search.ContainerSearchRepository;
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
 * Service Implementation for managing {@link Container}.
 */
@Service
@Transactional
public class ContainerServiceImpl implements ContainerService {

    private final Logger log = LoggerFactory.getLogger(ContainerServiceImpl.class);

    private final ContainerRepository containerRepository;

    private final ContainerSearchRepository containerSearchRepository;

    public ContainerServiceImpl(ContainerRepository containerRepository, ContainerSearchRepository containerSearchRepository) {
        this.containerRepository = containerRepository;
        this.containerSearchRepository = containerSearchRepository;
    }

    /**
     * Save a container.
     *
     * @param container the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Container save(Container container) {
        log.debug("Request to save Container : {}", container);
        Container result = containerRepository.save(container);
        containerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the containers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Container> findAll() {
        log.debug("Request to get all Containers");
        return containerRepository.findAll();
    }


    /**
     * Get one container by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Container> findOne(Long id) {
        log.debug("Request to get Container : {}", id);
        return containerRepository.findById(id);
    }

    /**
     * Delete the container by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Container : {}", id);
        containerRepository.deleteById(id);
        containerSearchRepository.deleteById(id);
    }

    /**
     * Search for the container corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Container> search(String query) {
        log.debug("Request to search Containers for query {}", query);
        return StreamSupport
            .stream(containerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
