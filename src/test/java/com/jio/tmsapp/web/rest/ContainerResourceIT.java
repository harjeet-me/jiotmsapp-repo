package com.jio.tmsapp.web.rest;

import com.jio.tmsapp.JiotmsappApp;
import com.jio.tmsapp.domain.Container;
import com.jio.tmsapp.repository.ContainerRepository;
import com.jio.tmsapp.repository.search.ContainerSearchRepository;
import com.jio.tmsapp.service.ContainerService;
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

/**
 * Integration tests for the {@link ContainerResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
public class ContainerResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;

    private static final String DEFAULT_INSURANCE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_PROVIDER = "BBBBBBBBBB";

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerService containerService;

    /**
     * This repository is mocked in the com.jio.tmsapp.repository.search test package.
     *
     * @see com.jio.tmsapp.repository.search.ContainerSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContainerSearchRepository mockContainerSearchRepository;

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

    private MockMvc restContainerMockMvc;

    private Container container;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContainerResource containerResource = new ContainerResource(containerService);
        this.restContainerMockMvc = MockMvcBuilders.standaloneSetup(containerResource)
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
    public static Container createEntity(EntityManager em) {
        Container container = new Container()
            .company(DEFAULT_COMPANY)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .insuranceProvider(DEFAULT_INSURANCE_PROVIDER);
        return container;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createUpdatedEntity(EntityManager em) {
        Container container = new Container()
            .company(UPDATED_COMPANY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .insuranceProvider(UPDATED_INSURANCE_PROVIDER);
        return container;
    }

    @BeforeEach
    public void initTest() {
        container = createEntity(em);
    }

    @Test
    @Transactional
    public void createContainer() throws Exception {
        int databaseSizeBeforeCreate = containerRepository.findAll().size();

        // Create the Container
        restContainerMockMvc.perform(post("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(container)))
            .andExpect(status().isCreated());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeCreate + 1);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testContainer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContainer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContainer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContainer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testContainer.getInsuranceProvider()).isEqualTo(DEFAULT_INSURANCE_PROVIDER);

        // Validate the Container in Elasticsearch
        verify(mockContainerSearchRepository, times(1)).save(testContainer);
    }

    @Test
    @Transactional
    public void createContainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = containerRepository.findAll().size();

        // Create the Container with an existing ID
        container.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerMockMvc.perform(post("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(container)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Container in Elasticsearch
        verify(mockContainerSearchRepository, times(0)).save(container);
    }


    @Test
    @Transactional
    public void getAllContainers() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        // Get all the containerList
        restContainerMockMvc.perform(get("/api/containers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(container.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].insuranceProvider").value(hasItem(DEFAULT_INSURANCE_PROVIDER)));
    }
    
    @Test
    @Transactional
    public void getContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        // Get the container
        restContainerMockMvc.perform(get("/api/containers/{id}", container.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(container.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.insuranceProvider").value(DEFAULT_INSURANCE_PROVIDER));
    }

    @Test
    @Transactional
    public void getNonExistingContainer() throws Exception {
        // Get the container
        restContainerMockMvc.perform(get("/api/containers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContainer() throws Exception {
        // Initialize the database
        containerService.save(container);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockContainerSearchRepository);

        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Update the container
        Container updatedContainer = containerRepository.findById(container.getId()).get();
        // Disconnect from session so that the updates on updatedContainer are not directly saved in db
        em.detach(updatedContainer);
        updatedContainer
            .company(UPDATED_COMPANY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .insuranceProvider(UPDATED_INSURANCE_PROVIDER);

        restContainerMockMvc.perform(put("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContainer)))
            .andExpect(status().isOk());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testContainer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContainer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContainer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContainer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testContainer.getInsuranceProvider()).isEqualTo(UPDATED_INSURANCE_PROVIDER);

        // Validate the Container in Elasticsearch
        verify(mockContainerSearchRepository, times(1)).save(testContainer);
    }

    @Test
    @Transactional
    public void updateNonExistingContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Create the Container

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc.perform(put("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(container)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Container in Elasticsearch
        verify(mockContainerSearchRepository, times(0)).save(container);
    }

    @Test
    @Transactional
    public void deleteContainer() throws Exception {
        // Initialize the database
        containerService.save(container);

        int databaseSizeBeforeDelete = containerRepository.findAll().size();

        // Delete the container
        restContainerMockMvc.perform(delete("/api/containers/{id}", container.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Container in Elasticsearch
        verify(mockContainerSearchRepository, times(1)).deleteById(container.getId());
    }

    @Test
    @Transactional
    public void searchContainer() throws Exception {
        // Initialize the database
        containerService.save(container);
        when(mockContainerSearchRepository.search(queryStringQuery("id:" + container.getId())))
            .thenReturn(Collections.singletonList(container));
        // Search the container
        restContainerMockMvc.perform(get("/api/_search/containers?query=id:" + container.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(container.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].insuranceProvider").value(hasItem(DEFAULT_INSURANCE_PROVIDER)));
    }
}
