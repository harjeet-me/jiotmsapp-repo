package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.EquipmentService;
import com.jio.tmsapp.domain.Equipment;
import com.jio.tmsapp.repository.EquipmentRepository;
import com.jio.tmsapp.repository.search.EquipmentSearchRepository;
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
 * Service Implementation for managing {@link Equipment}.
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private final EquipmentRepository equipmentRepository;

    private final EquipmentSearchRepository equipmentSearchRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, EquipmentSearchRepository equipmentSearchRepository) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentSearchRepository = equipmentSearchRepository;
    }

    /**
     * Save a equipment.
     *
     * @param equipment the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Equipment save(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        Equipment result = equipmentRepository.save(equipment);
        equipmentSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the equipment.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Equipment> findAll() {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll();
    }


    /**
     * Get one equipment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Equipment> findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        return equipmentRepository.findById(id);
    }

    /**
     * Delete the equipment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.deleteById(id);
        equipmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the equipment corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Equipment> search(String query) {
        log.debug("Request to search Equipment for query {}", query);
        return StreamSupport
            .stream(equipmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
