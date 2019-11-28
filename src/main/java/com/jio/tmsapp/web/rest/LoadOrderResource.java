package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.domain.LoadOrder;
import com.jio.tmsapp.service.LoadOrderService;
import com.jio.tmsapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.jio.tmsapp.domain.LoadOrder}.
 */
@RestController
@RequestMapping("/api")
public class LoadOrderResource {

    private final Logger log = LoggerFactory.getLogger(LoadOrderResource.class);

    private static final String ENTITY_NAME = "loadOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoadOrderService loadOrderService;

    public LoadOrderResource(LoadOrderService loadOrderService) {
        this.loadOrderService = loadOrderService;
    }

    /**
     * {@code POST  /load-orders} : Create a new loadOrder.
     *
     * @param loadOrder the loadOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loadOrder, or with status {@code 400 (Bad Request)} if the loadOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/load-orders")
    public ResponseEntity<LoadOrder> createLoadOrder(@RequestBody LoadOrder loadOrder) throws URISyntaxException {
        log.debug("REST request to save LoadOrder : {}", loadOrder);
        if (loadOrder.getId() != null) {
            throw new BadRequestAlertException("A new loadOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoadOrder result = loadOrderService.save(loadOrder);
        return ResponseEntity.created(new URI("/api/load-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /load-orders} : Updates an existing loadOrder.
     *
     * @param loadOrder the loadOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loadOrder,
     * or with status {@code 400 (Bad Request)} if the loadOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loadOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/load-orders")
    public ResponseEntity<LoadOrder> updateLoadOrder(@RequestBody LoadOrder loadOrder) throws URISyntaxException {
        log.debug("REST request to update LoadOrder : {}", loadOrder);
        if (loadOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoadOrder result = loadOrderService.save(loadOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loadOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /load-orders} : get all the loadOrders.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loadOrders in body.
     */
    @GetMapping("/load-orders")
    public ResponseEntity<List<LoadOrder>> getAllLoadOrders(Pageable pageable) {
        log.debug("REST request to get a page of LoadOrders");
        Page<LoadOrder> page = loadOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /load-orders/:id} : get the "id" loadOrder.
     *
     * @param id the id of the loadOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loadOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/load-orders/{id}")
    public ResponseEntity<LoadOrder> getLoadOrder(@PathVariable Long id) {
        log.debug("REST request to get LoadOrder : {}", id);
        Optional<LoadOrder> loadOrder = loadOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loadOrder);
    }

    /**
     * {@code DELETE  /load-orders/:id} : delete the "id" loadOrder.
     *
     * @param id the id of the loadOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/load-orders/{id}")
    public ResponseEntity<Void> deleteLoadOrder(@PathVariable Long id) {
        log.debug("REST request to delete LoadOrder : {}", id);
        loadOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/load-orders?query=:query} : search for the loadOrder corresponding
     * to the query.
     *
     * @param query the query of the loadOrder search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/load-orders")
    public ResponseEntity<List<LoadOrder>> searchLoadOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LoadOrders for query {}", query);
        Page<LoadOrder> page = loadOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
