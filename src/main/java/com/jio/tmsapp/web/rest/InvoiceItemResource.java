package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.domain.InvoiceItem;
import com.jio.tmsapp.service.InvoiceItemService;
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
 * REST controller for managing {@link com.jio.tmsapp.domain.InvoiceItem}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceItemResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemResource.class);

    private static final String ENTITY_NAME = "invoiceItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceItemService invoiceItemService;

    public InvoiceItemResource(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    /**
     * {@code POST  /invoice-items} : Create a new invoiceItem.
     *
     * @param invoiceItem the invoiceItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceItem, or with status {@code 400 (Bad Request)} if the invoiceItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-items")
    public ResponseEntity<InvoiceItem> createInvoiceItem(@RequestBody InvoiceItem invoiceItem) throws URISyntaxException {
        log.debug("REST request to save InvoiceItem : {}", invoiceItem);
        if (invoiceItem.getId() != null) {
            throw new BadRequestAlertException("A new invoiceItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceItem result = invoiceItemService.save(invoiceItem);
        return ResponseEntity.created(new URI("/api/invoice-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-items} : Updates an existing invoiceItem.
     *
     * @param invoiceItem the invoiceItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceItem,
     * or with status {@code 400 (Bad Request)} if the invoiceItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-items")
    public ResponseEntity<InvoiceItem> updateInvoiceItem(@RequestBody InvoiceItem invoiceItem) throws URISyntaxException {
        log.debug("REST request to update InvoiceItem : {}", invoiceItem);
        if (invoiceItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceItem result = invoiceItemService.save(invoiceItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-items} : get all the invoiceItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceItems in body.
     */
    @GetMapping("/invoice-items")
    public List<InvoiceItem> getAllInvoiceItems() {
        log.debug("REST request to get all InvoiceItems");
        return invoiceItemService.findAll();
    }

    /**
     * {@code GET  /invoice-items/:id} : get the "id" invoiceItem.
     *
     * @param id the id of the invoiceItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-items/{id}")
    public ResponseEntity<InvoiceItem> getInvoiceItem(@PathVariable Long id) {
        log.debug("REST request to get InvoiceItem : {}", id);
        Optional<InvoiceItem> invoiceItem = invoiceItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceItem);
    }

    /**
     * {@code DELETE  /invoice-items/:id} : delete the "id" invoiceItem.
     *
     * @param id the id of the invoiceItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-items/{id}")
    public ResponseEntity<Void> deleteInvoiceItem(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceItem : {}", id);
        invoiceItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/invoice-items?query=:query} : search for the invoiceItem corresponding
     * to the query.
     *
     * @param query the query of the invoiceItem search.
     * @return the result of the search.
     */
    @GetMapping("/_search/invoice-items")
    public List<InvoiceItem> searchInvoiceItems(@RequestParam String query) {
        log.debug("REST request to search InvoiceItems for query {}", query);
        return invoiceItemService.search(query);
    }
}
