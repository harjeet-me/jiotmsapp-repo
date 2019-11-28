package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.JiotmsappApp;
import com.jio.tmsapp.domain.Invoice;
import com.jio.tmsapp.repository.InvoiceRepository;
import com.jio.tmsapp.repository.search.InvoiceSearchRepository;
import com.jio.tmsapp.service.InvoiceService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
public class InvoiceResourceIT {

    private static final String DEFAULT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_INVOICE_TAX_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_TAX_TOTAL = 2D;

    private static final Double DEFAULT_INVOICE_SUB_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_SUB_TOTAL = 2D;

    private static final Double DEFAULT_INVOICE_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_TOTAL = 2D;

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_INVOICE_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final InvoiceStatus DEFAULT_STATUS = InvoiceStatus.DRAFT;
    private static final InvoiceStatus UPDATED_STATUS = InvoiceStatus.GENERATED;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    /**
     * This repository is mocked in the com.jio.tmsapp.repository.search test package.
     *
     * @see com.jio.tmsapp.repository.search.InvoiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceSearchRepository mockInvoiceSearchRepository;

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

    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceResource invoiceResource = new InvoiceResource(invoiceService);
        this.restInvoiceMockMvc = MockMvcBuilders.standaloneSetup(invoiceResource)
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
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .orderNo(DEFAULT_ORDER_NO)
            .invoiceTaxTotal(DEFAULT_INVOICE_TAX_TOTAL)
            .invoiceSubTotal(DEFAULT_INVOICE_SUB_TOTAL)
            .invoiceTotal(DEFAULT_INVOICE_TOTAL)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .invoiceDueDate(DEFAULT_INVOICE_DUE_DATE)
            .status(DEFAULT_STATUS);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .orderNo(UPDATED_ORDER_NO)
            .invoiceTaxTotal(UPDATED_INVOICE_TAX_TOTAL)
            .invoiceSubTotal(UPDATED_INVOICE_SUB_TOTAL)
            .invoiceTotal(UPDATED_INVOICE_TOTAL)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoiceDueDate(UPDATED_INVOICE_DUE_DATE)
            .status(UPDATED_STATUS);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testInvoice.getInvoiceTaxTotal()).isEqualTo(DEFAULT_INVOICE_TAX_TOTAL);
        assertThat(testInvoice.getInvoiceSubTotal()).isEqualTo(DEFAULT_INVOICE_SUB_TOTAL);
        assertThat(testInvoice.getInvoiceTotal()).isEqualTo(DEFAULT_INVOICE_TOTAL);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoice.getInvoiceDueDate()).isEqualTo(DEFAULT_INVOICE_DUE_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(1)).save(testInvoice);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(0)).save(invoice);
    }


    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].invoiceTaxTotal").value(hasItem(DEFAULT_INVOICE_TAX_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceSubTotal").value(hasItem(DEFAULT_INVOICE_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceTotal").value(hasItem(DEFAULT_INVOICE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDueDate").value(hasItem(DEFAULT_INVOICE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.invoiceTaxTotal").value(DEFAULT_INVOICE_TAX_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceSubTotal").value(DEFAULT_INVOICE_SUB_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceTotal").value(DEFAULT_INVOICE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.invoiceDueDate").value(DEFAULT_INVOICE_DUE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInvoiceSearchRepository);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .orderNo(UPDATED_ORDER_NO)
            .invoiceTaxTotal(UPDATED_INVOICE_TAX_TOTAL)
            .invoiceSubTotal(UPDATED_INVOICE_SUB_TOTAL)
            .invoiceTotal(UPDATED_INVOICE_TOTAL)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoiceDueDate(UPDATED_INVOICE_DUE_DATE)
            .status(UPDATED_STATUS);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testInvoice.getInvoiceTaxTotal()).isEqualTo(UPDATED_INVOICE_TAX_TOTAL);
        assertThat(testInvoice.getInvoiceSubTotal()).isEqualTo(UPDATED_INVOICE_SUB_TOTAL);
        assertThat(testInvoice.getInvoiceTotal()).isEqualTo(UPDATED_INVOICE_TOTAL);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoice.getInvoiceDueDate()).isEqualTo(UPDATED_INVOICE_DUE_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(1)).save(testInvoice);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Create the Invoice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(0)).save(invoice);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(1)).deleteById(invoice.getId());
    }

    @Test
    @Transactional
    public void searchInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);
        when(mockInvoiceSearchRepository.search(queryStringQuery("id:" + invoice.getId())))
            .thenReturn(Collections.singletonList(invoice));
        // Search the invoice
        restInvoiceMockMvc.perform(get("/api/_search/invoices?query=id:" + invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].invoiceTaxTotal").value(hasItem(DEFAULT_INVOICE_TAX_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceSubTotal").value(hasItem(DEFAULT_INVOICE_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceTotal").value(hasItem(DEFAULT_INVOICE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDueDate").value(hasItem(DEFAULT_INVOICE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
