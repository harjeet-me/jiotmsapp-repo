package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.domain.BookingItem;
import com.jio.tmsapp.service.BookingItemService;
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
 * REST controller for managing {@link com.jio.tmsapp.domain.BookingItem}.
 */
@RestController
@RequestMapping("/api")
public class BookingItemResource {

    private final Logger log = LoggerFactory.getLogger(BookingItemResource.class);

    private static final String ENTITY_NAME = "bookingItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookingItemService bookingItemService;

    public BookingItemResource(BookingItemService bookingItemService) {
        this.bookingItemService = bookingItemService;
    }

    /**
     * {@code POST  /booking-items} : Create a new bookingItem.
     *
     * @param bookingItem the bookingItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookingItem, or with status {@code 400 (Bad Request)} if the bookingItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/booking-items")
    public ResponseEntity<BookingItem> createBookingItem(@RequestBody BookingItem bookingItem) throws URISyntaxException {
        log.debug("REST request to save BookingItem : {}", bookingItem);
        if (bookingItem.getId() != null) {
            throw new BadRequestAlertException("A new bookingItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookingItem result = bookingItemService.save(bookingItem);
        return ResponseEntity.created(new URI("/api/booking-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /booking-items} : Updates an existing bookingItem.
     *
     * @param bookingItem the bookingItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingItem,
     * or with status {@code 400 (Bad Request)} if the bookingItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookingItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/booking-items")
    public ResponseEntity<BookingItem> updateBookingItem(@RequestBody BookingItem bookingItem) throws URISyntaxException {
        log.debug("REST request to update BookingItem : {}", bookingItem);
        if (bookingItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookingItem result = bookingItemService.save(bookingItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookingItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /booking-items} : get all the bookingItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookingItems in body.
     */
    @GetMapping("/booking-items")
    public List<BookingItem> getAllBookingItems() {
        log.debug("REST request to get all BookingItems");
        return bookingItemService.findAll();
    }

    /**
     * {@code GET  /booking-items/:id} : get the "id" bookingItem.
     *
     * @param id the id of the bookingItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookingItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/booking-items/{id}")
    public ResponseEntity<BookingItem> getBookingItem(@PathVariable Long id) {
        log.debug("REST request to get BookingItem : {}", id);
        Optional<BookingItem> bookingItem = bookingItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookingItem);
    }

    /**
     * {@code DELETE  /booking-items/:id} : delete the "id" bookingItem.
     *
     * @param id the id of the bookingItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/booking-items/{id}")
    public ResponseEntity<Void> deleteBookingItem(@PathVariable Long id) {
        log.debug("REST request to delete BookingItem : {}", id);
        bookingItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/booking-items?query=:query} : search for the bookingItem corresponding
     * to the query.
     *
     * @param query the query of the bookingItem search.
     * @return the result of the search.
     */
    @GetMapping("/_search/booking-items")
    public List<BookingItem> searchBookingItems(@RequestParam String query) {
        log.debug("REST request to search BookingItems for query {}", query);
        return bookingItemService.search(query);
    }
}
