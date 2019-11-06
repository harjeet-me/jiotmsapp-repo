package com.jio.tmsapp.service.impl;

import com.jio.tmsapp.service.BookingItemService;
import com.jio.tmsapp.domain.BookingItem;
import com.jio.tmsapp.repository.BookingItemRepository;
import com.jio.tmsapp.repository.search.BookingItemSearchRepository;
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
 * Service Implementation for managing {@link BookingItem}.
 */
@Service
@Transactional
public class BookingItemServiceImpl implements BookingItemService {

    private final Logger log = LoggerFactory.getLogger(BookingItemServiceImpl.class);

    private final BookingItemRepository bookingItemRepository;

    private final BookingItemSearchRepository bookingItemSearchRepository;

    public BookingItemServiceImpl(BookingItemRepository bookingItemRepository, BookingItemSearchRepository bookingItemSearchRepository) {
        this.bookingItemRepository = bookingItemRepository;
        this.bookingItemSearchRepository = bookingItemSearchRepository;
    }

    /**
     * Save a bookingItem.
     *
     * @param bookingItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BookingItem save(BookingItem bookingItem) {
        log.debug("Request to save BookingItem : {}", bookingItem);
        BookingItem result = bookingItemRepository.save(bookingItem);
        bookingItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bookingItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookingItem> findAll() {
        log.debug("Request to get all BookingItems");
        return bookingItemRepository.findAll();
    }


    /**
     * Get one bookingItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookingItem> findOne(Long id) {
        log.debug("Request to get BookingItem : {}", id);
        return bookingItemRepository.findById(id);
    }

    /**
     * Delete the bookingItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookingItem : {}", id);
        bookingItemRepository.deleteById(id);
        bookingItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the bookingItem corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookingItem> search(String query) {
        log.debug("Request to search BookingItems for query {}", query);
        return StreamSupport
            .stream(bookingItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
