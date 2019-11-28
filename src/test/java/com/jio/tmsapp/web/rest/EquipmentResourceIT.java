package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.JiotmsappApp;
import com.jio.tmsapp.domain.Equipment;
import com.jio.tmsapp.repository.EquipmentRepository;
import com.jio.tmsapp.repository.search.EquipmentSearchRepository;
import com.jio.tmsapp.service.EquipmentService;
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

import com.jio.tmsapp.domain.enumeration.EquipmentEnum;
import com.jio.tmsapp.domain.enumeration.SizeEnum;
/**
 * Integration tests for the {@link EquipmentResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
public class EquipmentResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final EquipmentEnum DEFAULT_TYPE = EquipmentEnum.TRAILER;
    private static final EquipmentEnum UPDATED_TYPE = EquipmentEnum.CONTAINER;

    private static final SizeEnum DEFAULT_SIZE = SizeEnum.FIFTYTHREE;
    private static final SizeEnum UPDATED_SIZE = SizeEnum.FORTYTHREE;

    private static final String DEFAULT_INSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE = "BBBBBBBBBB";

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentService equipmentService;

    /**
     * This repository is mocked in the com.jio.tmsapp.repository.search test package.
     *
     * @see com.jio.tmsapp.repository.search.EquipmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private EquipmentSearchRepository mockEquipmentSearchRepository;

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

    private MockMvc restEquipmentMockMvc;

    private Equipment equipment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipmentResource equipmentResource = new EquipmentResource(equipmentService);
        this.restEquipmentMockMvc = MockMvcBuilders.standaloneSetup(equipmentResource)
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
    public static Equipment createEntity(EntityManager em) {
        Equipment equipment = new Equipment()
            .number(DEFAULT_NUMBER)
            .type(DEFAULT_TYPE)
            .size(DEFAULT_SIZE)
            .insurance(DEFAULT_INSURANCE);
        return equipment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipment createUpdatedEntity(EntityManager em) {
        Equipment equipment = new Equipment()
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .size(UPDATED_SIZE)
            .insurance(UPDATED_INSURANCE);
        return equipment;
    }

    @BeforeEach
    public void initTest() {
        equipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipment() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isCreated());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testEquipment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEquipment.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testEquipment.getInsurance()).isEqualTo(DEFAULT_INSURANCE);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(1)).save(testEquipment);
    }

    @Test
    @Transactional
    public void createEquipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment with an existing ID
        equipment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(0)).save(equipment);
    }


    @Test
    @Transactional
    public void getAllEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList
        restEquipmentMockMvc.perform(get("/api/equipment?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(DEFAULT_INSURANCE)));
    }
    
    @Test
    @Transactional
    public void getEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipment.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.insurance").value(DEFAULT_INSURANCE));
    }

    @Test
    @Transactional
    public void getNonExistingEquipment() throws Exception {
        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipment() throws Exception {
        // Initialize the database
        equipmentService.save(equipment);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEquipmentSearchRepository);

        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment
        Equipment updatedEquipment = equipmentRepository.findById(equipment.getId()).get();
        // Disconnect from session so that the updates on updatedEquipment are not directly saved in db
        em.detach(updatedEquipment);
        updatedEquipment
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .size(UPDATED_SIZE)
            .insurance(UPDATED_INSURANCE);

        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipment)))
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEquipment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEquipment.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testEquipment.getInsurance()).isEqualTo(UPDATED_INSURANCE);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(1)).save(testEquipment);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Create the Equipment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(0)).save(equipment);
    }

    @Test
    @Transactional
    public void deleteEquipment() throws Exception {
        // Initialize the database
        equipmentService.save(equipment);

        int databaseSizeBeforeDelete = equipmentRepository.findAll().size();

        // Delete the equipment
        restEquipmentMockMvc.perform(delete("/api/equipment/{id}", equipment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(1)).deleteById(equipment.getId());
    }

    @Test
    @Transactional
    public void searchEquipment() throws Exception {
        // Initialize the database
        equipmentService.save(equipment);
        when(mockEquipmentSearchRepository.search(queryStringQuery("id:" + equipment.getId())))
            .thenReturn(Collections.singletonList(equipment));
        // Search the equipment
        restEquipmentMockMvc.perform(get("/api/_search/equipment?query=id:" + equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(DEFAULT_INSURANCE)));
    }
}
