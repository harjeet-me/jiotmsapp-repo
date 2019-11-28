package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.JiotmsappApp;
import com.jio.tmsapp.domain.BookingItem;
import com.jio.tmsapp.repository.BookingItemRepository;
import com.jio.tmsapp.repository.search.BookingItemSearchRepository;
import com.jio.tmsapp.service.BookingItemService;
import com.jio.tmsapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.jio.tmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jio.tmsapp.domain.enumeration.StatusEnum;
/**
 * Integration tests for the {@link BookingItemResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
public class BookingItemResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_PICKUP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKUP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DROP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DROP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LOCATION = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.PICKEDUP;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.ONROAD;

    private static final Long DEFAULT_DETENTION = 1L;
    private static final Long UPDATED_DETENTION = 2L;

    private static final Instant DEFAULT_CHASIS_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHASIS_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_POD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POD_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_HAZMAT = false;
    private static final Boolean UPDATED_HAZMAT = true;

    private static final String DEFAULT_RECIEVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_RECIEVED_BY = "BBBBBBBBBB";

    @Autowired
    private BookingItemRepository bookingItemRepository;

    @Autowired
    private BookingItemService bookingItemService;

    /**
     * This repository is mocked in the com.jio.tmsapp.repository.search test package.
     *
     * @see com.jio.tmsapp.repository.search.BookingItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private BookingItemSearchRepository mockBookingItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBookingItemMockMvc;

    private BookingItem bookingItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookingItemResource bookingItemResource = new BookingItemResource(bookingItemService);
        this.restBookingItemMockMvc = MockMvcBuilders.standaloneSetup(bookingItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingItem createEntity(EntityManager em) {
        BookingItem bookingItem = new BookingItem()
            .description(DEFAULT_DESCRIPTION)
            .pickup(DEFAULT_PICKUP)
            .drop(DEFAULT_DROP)
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .currentLocation(DEFAULT_CURRENT_LOCATION)
            .status(DEFAULT_STATUS)
            .detention(DEFAULT_DETENTION)
            .chasisInTime(DEFAULT_CHASIS_IN_TIME)
            .pod(DEFAULT_POD)
            .podContentType(DEFAULT_POD_CONTENT_TYPE)
            .hazmat(DEFAULT_HAZMAT)
            .recievedBy(DEFAULT_RECIEVED_BY);
        return bookingItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingItem createUpdatedEntity(EntityManager em) {
        BookingItem bookingItem = new BookingItem()
            .description(UPDATED_DESCRIPTION)
            .pickup(UPDATED_PICKUP)
            .drop(UPDATED_DROP)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .currentLocation(UPDATED_CURRENT_LOCATION)
            .status(UPDATED_STATUS)
            .detention(UPDATED_DETENTION)
            .chasisInTime(UPDATED_CHASIS_IN_TIME)
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .hazmat(UPDATED_HAZMAT)
            .recievedBy(UPDATED_RECIEVED_BY);
        return bookingItem;
    }

    @BeforeEach
    public void initTest() {
        bookingItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookingItem() throws Exception {
        int databaseSizeBeforeCreate = bookingItemRepository.findAll().size();

        // Create the BookingItem
        restBookingItemMockMvc.perform(post("/api/booking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingItem)))
            .andExpect(status().isCreated());

        // Validate the BookingItem in the database
        List<BookingItem> bookingItemList = bookingItemRepository.findAll();
        assertThat(bookingItemList).hasSize(databaseSizeBeforeCreate + 1);
        BookingItem testBookingItem = bookingItemList.get(bookingItemList.size() - 1);
        assertThat(testBookingItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBookingItem.getPickup()).isEqualTo(DEFAULT_PICKUP);
        assertThat(testBookingItem.getDrop()).isEqualTo(DEFAULT_DROP);
        assertThat(testBookingItem.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testBookingItem.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testBookingItem.getCurrentLocation()).isEqualTo(DEFAULT_CURRENT_LOCATION);
        assertThat(testBookingItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBookingItem.getDetention()).isEqualTo(DEFAULT_DETENTION);
        assertThat(testBookingItem.getChasisInTime()).isEqualTo(DEFAULT_CHASIS_IN_TIME);
        assertThat(testBookingItem.getPod()).isEqualTo(DEFAULT_POD);
        assertThat(testBookingItem.getPodContentType()).isEqualTo(DEFAULT_POD_CONTENT_TYPE);
        assertThat(testBookingItem.isHazmat()).isEqualTo(DEFAULT_HAZMAT);
        assertThat(testBookingItem.getRecievedBy()).isEqualTo(DEFAULT_RECIEVED_BY);

        // Validate the BookingItem in Elasticsearch
        verify(mockBookingItemSearchRepository, times(1)).save(testBookingItem);
    }

    @Test
    @Transactional
    public void createBookingItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingItemRepository.findAll().size();

        // Create the BookingItem with an existing ID
        bookingItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingItemMockMvc.perform(post("/api/booking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingItem)))
            .andExpect(status().isBadRequest());

        // Validate the BookingItem in the database
        List<BookingItem> bookingItemList = bookingItemRepository.findAll();
        assertThat(bookingItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the BookingItem in Elasticsearch
        verify(mockBookingItemSearchRepository, times(0)).save(bookingItem);
    }


    @Test
    @Transactional
    public void getAllBookingItems() throws Exception {
        // Initialize the database
        bookingItemRepository.saveAndFlush(bookingItem);

        // Get all the bookingItemList
        restBookingItemMockMvc.perform(get("/api/booking-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pickup").value(hasItem(DEFAULT_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].drop").value(hasItem(DEFAULT_DROP.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].detention").value(hasItem(DEFAULT_DETENTION.intValue())))
            .andExpect(jsonPath("$.[*].chasisInTime").value(hasItem(DEFAULT_CHASIS_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].hazmat").value(hasItem(DEFAULT_HAZMAT.booleanValue())))
            .andExpect(jsonPath("$.[*].recievedBy").value(hasItem(DEFAULT_RECIEVED_BY)));
    }
    
    @Test
    @Transactional
    public void getBookingItem() throws Exception {
        // Initialize the database
        bookingItemRepository.saveAndFlush(bookingItem);

        // Get the bookingItem
        restBookingItemMockMvc.perform(get("/api/booking-items/{id}", bookingItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookingItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.pickup").value(DEFAULT_PICKUP.toString()))
            .andExpect(jsonPath("$.drop").value(DEFAULT_DROP.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.currentLocation").value(DEFAULT_CURRENT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.detention").value(DEFAULT_DETENTION.intValue()))
            .andExpect(jsonPath("$.chasisInTime").value(DEFAULT_CHASIS_IN_TIME.toString()))
            .andExpect(jsonPath("$.podContentType").value(DEFAULT_POD_CONTENT_TYPE))
            .andExpect(jsonPath("$.pod").value(Base64Utils.encodeToString(DEFAULT_POD)))
            .andExpect(jsonPath("$.hazmat").value(DEFAULT_HAZMAT.booleanValue()))
            .andExpect(jsonPath("$.recievedBy").value(DEFAULT_RECIEVED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingBookingItem() throws Exception {
        // Get the bookingItem
        restBookingItemMockMvc.perform(get("/api/booking-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookingItem() throws Exception {
        // Initialize the database
        bookingItemService.save(bookingItem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBookingItemSearchRepository);

        int databaseSizeBeforeUpdate = bookingItemRepository.findAll().size();

        // Update the bookingItem
        BookingItem updatedBookingItem = bookingItemRepository.findById(bookingItem.getId()).get();
        // Disconnect from session so that the updates on updatedBookingItem are not directly saved in db
        em.detach(updatedBookingItem);
        updatedBookingItem
            .description(UPDATED_DESCRIPTION)
            .pickup(UPDATED_PICKUP)
            .drop(UPDATED_DROP)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .currentLocation(UPDATED_CURRENT_LOCATION)
            .status(UPDATED_STATUS)
            .detention(UPDATED_DETENTION)
            .chasisInTime(UPDATED_CHASIS_IN_TIME)
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .hazmat(UPDATED_HAZMAT)
            .recievedBy(UPDATED_RECIEVED_BY);

        restBookingItemMockMvc.perform(put("/api/booking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookingItem)))
            .andExpect(status().isOk());

        // Validate the BookingItem in the database
        List<BookingItem> bookingItemList = bookingItemRepository.findAll();
        assertThat(bookingItemList).hasSize(databaseSizeBeforeUpdate);
        BookingItem testBookingItem = bookingItemList.get(bookingItemList.size() - 1);
        assertThat(testBookingItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBookingItem.getPickup()).isEqualTo(UPDATED_PICKUP);
        assertThat(testBookingItem.getDrop()).isEqualTo(UPDATED_DROP);
        assertThat(testBookingItem.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testBookingItem.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testBookingItem.getCurrentLocation()).isEqualTo(UPDATED_CURRENT_LOCATION);
        assertThat(testBookingItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBookingItem.getDetention()).isEqualTo(UPDATED_DETENTION);
        assertThat(testBookingItem.getChasisInTime()).isEqualTo(UPDATED_CHASIS_IN_TIME);
        assertThat(testBookingItem.getPod()).isEqualTo(UPDATED_POD);
        assertThat(testBookingItem.getPodContentType()).isEqualTo(UPDATED_POD_CONTENT_TYPE);
        assertThat(testBookingItem.isHazmat()).isEqualTo(UPDATED_HAZMAT);
        assertThat(testBookingItem.getRecievedBy()).isEqualTo(UPDATED_RECIEVED_BY);

        // Validate the BookingItem in Elasticsearch
        verify(mockBookingItemSearchRepository, times(1)).save(testBookingItem);
    }

    @Test
    @Transactional
    public void updateNonExistingBookingItem() throws Exception {
        int databaseSizeBeforeUpdate = bookingItemRepository.findAll().size();

        // Create the BookingItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingItemMockMvc.perform(put("/api/booking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingItem)))
            .andExpect(status().isBadRequest());

        // Validate the BookingItem in the database
        List<BookingItem> bookingItemList = bookingItemRepository.findAll();
        assertThat(bookingItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookingItem in Elasticsearch
        verify(mockBookingItemSearchRepository, times(0)).save(bookingItem);
    }

    @Test
    @Transactional
    public void deleteBookingItem() throws Exception {
        // Initialize the database
        bookingItemService.save(bookingItem);

        int databaseSizeBeforeDelete = bookingItemRepository.findAll().size();

        // Delete the bookingItem
        restBookingItemMockMvc.perform(delete("/api/booking-items/{id}", bookingItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookingItem> bookingItemList = bookingItemRepository.findAll();
        assertThat(bookingItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BookingItem in Elasticsearch
        verify(mockBookingItemSearchRepository, times(1)).deleteById(bookingItem.getId());
    }

    @Test
    @Transactional
    public void searchBookingItem() throws Exception {
        // Initialize the database
        bookingItemService.save(bookingItem);
        when(mockBookingItemSearchRepository.search(queryStringQuery("id:" + bookingItem.getId())))
            .thenReturn(Collections.singletonList(bookingItem));
        // Search the bookingItem
        restBookingItemMockMvc.perform(get("/api/_search/booking-items?query=id:" + bookingItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pickup").value(hasItem(DEFAULT_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].drop").value(hasItem(DEFAULT_DROP.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].detention").value(hasItem(DEFAULT_DETENTION.intValue())))
            .andExpect(jsonPath("$.[*].chasisInTime").value(hasItem(DEFAULT_CHASIS_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].hazmat").value(hasItem(DEFAULT_HAZMAT.booleanValue())))
            .andExpect(jsonPath("$.[*].recievedBy").value(hasItem(DEFAULT_RECIEVED_BY)));
    }
}
