package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.domain.Equipment;
import com.jio.tmsapp.service.EquipmentService;
import com.jio.tmsapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.jio.tmsapp.domain.Equipment}.
 */
@RestController
@RequestMapping("/api")
public class EquipmentResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentResource.class);

    private static final String ENTITY_NAME = "equipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentService equipmentService;

    public EquipmentResource(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /**
     * {@code POST  /equipment} : Create a new equipment.
     *
     * @param equipment the equipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipment, or with status {@code 400 (Bad Request)} if the equipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipment")
    public ResponseEntity<Equipment> createEquipment(@RequestBody Equipment equipment) throws URISyntaxException {
        log.debug("REST request to save Equipment : {}", equipment);
        if (equipment.getId() != null) {
            throw new BadRequestAlertException("A new equipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipment result = equipmentService.save(equipment);
        return ResponseEntity.created(new URI("/api/equipment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipment} : Updates an existing equipment.
     *
     * @param equipment the equipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipment,
     * or with status {@code 400 (Bad Request)} if the equipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipment")
    public ResponseEntity<Equipment> updateEquipment(@RequestBody Equipment equipment) throws URISyntaxException {
        log.debug("REST request to update Equipment : {}", equipment);
        if (equipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Equipment result = equipmentService.save(equipment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipment} : get all the equipment.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipment in body.
     */
    @GetMapping("/equipment")
    public List<Equipment> getAllEquipment() {
        log.debug("REST request to get all Equipment");
        return equipmentService.findAll();
    }

    /**
     * {@code GET  /equipment/:id} : get the "id" equipment.
     *
     * @param id the id of the equipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipment/{id}")
    public ResponseEntity<Equipment> getEquipment(@PathVariable Long id) {
        log.debug("REST request to get Equipment : {}", id);
        Optional<Equipment> equipment = equipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipment);
    }

    /**
     * {@code DELETE  /equipment/:id} : delete the "id" equipment.
     *
     * @param id the id of the equipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        log.debug("REST request to delete Equipment : {}", id);
        equipmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/equipment?query=:query} : search for the equipment corresponding
     * to the query.
     *
     * @param query the query of the equipment search.
     * @return the result of the search.
     */
    @GetMapping("/_search/equipment")
    public List<Equipment> searchEquipment(@RequestParam String query) {
        log.debug("REST request to search Equipment for query {}", query);
        return equipmentService.search(query);
    }
}
