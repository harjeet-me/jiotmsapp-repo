package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.JiotmsappApp;
import com.jio.tmsapp.domain.InvoiceItem;
import com.jio.tmsapp.repository.InvoiceItemRepository;
import com.jio.tmsapp.repository.search.InvoiceItemSearchRepository;
import com.jio.tmsapp.service.InvoiceItemService;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.jio.tmsapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jio.tmsapp.domain.enumeration.InvoiceStatus;
/**
 * Integration tests for the {@link InvoiceItemResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
public class InvoiceItemResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final InvoiceStatus DEFAULT_STATUS = InvoiceStatus.DRAFT;
    private static final InvoiceStatus UPDATED_STATUS = InvoiceStatus.GENERATED;

    private static final String DEFAULT_SHIPMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BOL = "AAAAAAAAAA";
    private static final String UPDATED_BOL = "BBBBBBBBBB";

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private InvoiceItemService invoiceItemService;

    /**
     * This repository is mocked in the com.jio.tmsapp.repository.search test package.
     *
     * @see com.jio.tmsapp.repository.search.InvoiceItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceItemSearchRepository mockInvoiceItemSearchRepository;

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

    private MockMvc restInvoiceItemMockMvc;

    private InvoiceItem invoiceItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceItemResource invoiceItemResource = new InvoiceItemResource(invoiceItemService);
        this.restInvoiceItemMockMvc = MockMvcBuilders.standaloneSetup(invoiceItemResource)
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
    public static InvoiceItem createEntity(EntityManager em) {
        InvoiceItem invoiceItem = new InvoiceItem()
            .description(DEFAULT_DESCRIPTION)
            .qty(DEFAULT_QTY)
            .price(DEFAULT_PRICE)
            .total(DEFAULT_TOTAL)
            .status(DEFAULT_STATUS)
            .shipmentNumber(DEFAULT_SHIPMENT_NUMBER)
            .bol(DEFAULT_BOL);
        return invoiceItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItem createUpdatedEntity(EntityManager em) {
        InvoiceItem invoiceItem = new InvoiceItem()
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS)
            .shipmentNumber(UPDATED_SHIPMENT_NUMBER)
            .bol(UPDATED_BOL);
        return invoiceItem;
    }

    @BeforeEach
    public void initTest() {
        invoiceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceItem() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemRepository.findAll().size();

        // Create the InvoiceItem
        restInvoiceItemMockMvc.perform(post("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isCreated());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testInvoiceItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testInvoiceItem.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testInvoiceItem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoiceItem.getShipmentNumber()).isEqualTo(DEFAULT_SHIPMENT_NUMBER);
        assertThat(testInvoiceItem.getBol()).isEqualTo(DEFAULT_BOL);

        // Validate the InvoiceItem in Elasticsearch
        verify(mockInvoiceItemSearchRepository, times(1)).save(testInvoiceItem);
    }

    @Test
    @Transactional
    public void createInvoiceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemRepository.findAll().size();

        // Create the InvoiceItem with an existing ID
        invoiceItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceItemMockMvc.perform(post("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the InvoiceItem in Elasticsearch
        verify(mockInvoiceItemSearchRepository, times(0)).save(invoiceItem);
    }


    @Test
    @Transactional
    public void getAllInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList
        restInvoiceItemMockMvc.perform(get("/api/invoice-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].shipmentNumber").value(hasItem(DEFAULT_SHIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].bol").value(hasItem(DEFAULT_BOL)));
    }
    
    @Test
    @Transactional
    public void getInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/invoice-items/{id}", invoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceItem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.shipmentNumber").value(DEFAULT_SHIPMENT_NUMBER))
            .andExpect(jsonPath("$.bol").value(DEFAULT_BOL));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceItem() throws Exception {
        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/invoice-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemService.save(invoiceItem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInvoiceItemSearchRepository);

        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // Update the invoiceItem
        InvoiceItem updatedInvoiceItem = invoiceItemRepository.findById(invoiceItem.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceItem are not directly saved in db
        em.detach(updatedInvoiceItem);
        updatedInvoiceItem
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS)
            .shipmentNumber(UPDATED_SHIPMENT_NUMBER)
            .bol(UPDATED_BOL);

        restInvoiceItemMockMvc.perform(put("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceItem)))
            .andExpect(status().isOk());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testInvoiceItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testInvoiceItem.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testInvoiceItem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoiceItem.getShipmentNumber()).isEqualTo(UPDATED_SHIPMENT_NUMBER);
        assertThat(testInvoiceItem.getBol()).isEqualTo(UPDATED_BOL);

        // Validate the InvoiceItem in Elasticsearch
        verify(mockInvoiceItemSearchRepository, times(1)).save(testInvoiceItem);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // Create the InvoiceItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc.perform(put("/api/invoice-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InvoiceItem in Elasticsearch
        verify(mockInvoiceItemSearchRepository, times(0)).save(invoiceItem);
    }

    @Test
    @Transactional
    public void deleteInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemService.save(invoiceItem);

        int databaseSizeBeforeDelete = invoiceItemRepository.findAll().size();

        // Delete the invoiceItem
        restInvoiceItemMockMvc.perform(delete("/api/invoice-items/{id}", invoiceItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InvoiceItem in Elasticsearch
        verify(mockInvoiceItemSearchRepository, times(1)).deleteById(invoiceItem.getId());
    }

    @Test
    @Transactional
    public void searchInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemService.save(invoiceItem);
        when(mockInvoiceItemSearchRepository.search(queryStringQuery("id:" + invoiceItem.getId())))
            .thenReturn(Collections.singletonList(invoiceItem));
        // Search the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/_search/invoice-items?query=id:" + invoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].shipmentNumber").value(hasItem(DEFAULT_SHIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].bol").value(hasItem(DEFAULT_BOL)));
    }
}
