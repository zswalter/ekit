package com.afreecar.main.web.rest;

import com.afreecar.main.AfreecarApp;
import com.afreecar.main.domain.TicketSurveyMap;
import com.afreecar.main.repository.TicketSurveyMapRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TicketSurveyMapResource} REST controller.
 */
@SpringBootTest(classes = AfreecarApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketSurveyMapResourceIT {

    private static final Integer DEFAULT_ID_TICKET = 1;
    private static final Integer UPDATED_ID_TICKET = 2;

    private static final Integer DEFAULT_ID_SURVEY_ANSWER = 1;
    private static final Integer UPDATED_ID_SURVEY_ANSWER = 2;

    @Autowired
    private TicketSurveyMapRepository ticketSurveyMapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketSurveyMapMockMvc;

    private TicketSurveyMap ticketSurveyMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketSurveyMap createEntity(EntityManager em) {
        TicketSurveyMap ticketSurveyMap = new TicketSurveyMap()
            .idTicket(DEFAULT_ID_TICKET)
            .idSurveyAnswer(DEFAULT_ID_SURVEY_ANSWER);
        return ticketSurveyMap;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketSurveyMap createUpdatedEntity(EntityManager em) {
        TicketSurveyMap ticketSurveyMap = new TicketSurveyMap()
            .idTicket(UPDATED_ID_TICKET)
            .idSurveyAnswer(UPDATED_ID_SURVEY_ANSWER);
        return ticketSurveyMap;
    }

    @BeforeEach
    public void initTest() {
        ticketSurveyMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketSurveyMap() throws Exception {
        int databaseSizeBeforeCreate = ticketSurveyMapRepository.findAll().size();
        // Create the TicketSurveyMap
        restTicketSurveyMapMockMvc.perform(post("/api/ticket-survey-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketSurveyMap)))
            .andExpect(status().isCreated());

        // Validate the TicketSurveyMap in the database
        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeCreate + 1);
        TicketSurveyMap testTicketSurveyMap = ticketSurveyMapList.get(ticketSurveyMapList.size() - 1);
        assertThat(testTicketSurveyMap.getIdTicket()).isEqualTo(DEFAULT_ID_TICKET);
        assertThat(testTicketSurveyMap.getIdSurveyAnswer()).isEqualTo(DEFAULT_ID_SURVEY_ANSWER);
    }

    @Test
    @Transactional
    public void createTicketSurveyMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketSurveyMapRepository.findAll().size();

        // Create the TicketSurveyMap with an existing ID
        ticketSurveyMap.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketSurveyMapMockMvc.perform(post("/api/ticket-survey-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketSurveyMap)))
            .andExpect(status().isBadRequest());

        // Validate the TicketSurveyMap in the database
        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdTicketIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketSurveyMapRepository.findAll().size();
        // set the field null
        ticketSurveyMap.setIdTicket(null);

        // Create the TicketSurveyMap, which fails.


        restTicketSurveyMapMockMvc.perform(post("/api/ticket-survey-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketSurveyMap)))
            .andExpect(status().isBadRequest());

        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdSurveyAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketSurveyMapRepository.findAll().size();
        // set the field null
        ticketSurveyMap.setIdSurveyAnswer(null);

        // Create the TicketSurveyMap, which fails.


        restTicketSurveyMapMockMvc.perform(post("/api/ticket-survey-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketSurveyMap)))
            .andExpect(status().isBadRequest());

        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTicketSurveyMaps() throws Exception {
        // Initialize the database
        ticketSurveyMapRepository.saveAndFlush(ticketSurveyMap);

        // Get all the ticketSurveyMapList
        restTicketSurveyMapMockMvc.perform(get("/api/ticket-survey-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketSurveyMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTicket").value(hasItem(DEFAULT_ID_TICKET)))
            .andExpect(jsonPath("$.[*].idSurveyAnswer").value(hasItem(DEFAULT_ID_SURVEY_ANSWER)));
    }
    
    @Test
    @Transactional
    public void getTicketSurveyMap() throws Exception {
        // Initialize the database
        ticketSurveyMapRepository.saveAndFlush(ticketSurveyMap);

        // Get the ticketSurveyMap
        restTicketSurveyMapMockMvc.perform(get("/api/ticket-survey-maps/{id}", ticketSurveyMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketSurveyMap.getId().intValue()))
            .andExpect(jsonPath("$.idTicket").value(DEFAULT_ID_TICKET))
            .andExpect(jsonPath("$.idSurveyAnswer").value(DEFAULT_ID_SURVEY_ANSWER));
    }
    @Test
    @Transactional
    public void getNonExistingTicketSurveyMap() throws Exception {
        // Get the ticketSurveyMap
        restTicketSurveyMapMockMvc.perform(get("/api/ticket-survey-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketSurveyMap() throws Exception {
        // Initialize the database
        ticketSurveyMapRepository.saveAndFlush(ticketSurveyMap);

        int databaseSizeBeforeUpdate = ticketSurveyMapRepository.findAll().size();

        // Update the ticketSurveyMap
        TicketSurveyMap updatedTicketSurveyMap = ticketSurveyMapRepository.findById(ticketSurveyMap.getId()).get();
        // Disconnect from session so that the updates on updatedTicketSurveyMap are not directly saved in db
        em.detach(updatedTicketSurveyMap);
        updatedTicketSurveyMap
            .idTicket(UPDATED_ID_TICKET)
            .idSurveyAnswer(UPDATED_ID_SURVEY_ANSWER);

        restTicketSurveyMapMockMvc.perform(put("/api/ticket-survey-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTicketSurveyMap)))
            .andExpect(status().isOk());

        // Validate the TicketSurveyMap in the database
        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeUpdate);
        TicketSurveyMap testTicketSurveyMap = ticketSurveyMapList.get(ticketSurveyMapList.size() - 1);
        assertThat(testTicketSurveyMap.getIdTicket()).isEqualTo(UPDATED_ID_TICKET);
        assertThat(testTicketSurveyMap.getIdSurveyAnswer()).isEqualTo(UPDATED_ID_SURVEY_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketSurveyMap() throws Exception {
        int databaseSizeBeforeUpdate = ticketSurveyMapRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketSurveyMapMockMvc.perform(put("/api/ticket-survey-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketSurveyMap)))
            .andExpect(status().isBadRequest());

        // Validate the TicketSurveyMap in the database
        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTicketSurveyMap() throws Exception {
        // Initialize the database
        ticketSurveyMapRepository.saveAndFlush(ticketSurveyMap);

        int databaseSizeBeforeDelete = ticketSurveyMapRepository.findAll().size();

        // Delete the ticketSurveyMap
        restTicketSurveyMapMockMvc.perform(delete("/api/ticket-survey-maps/{id}", ticketSurveyMap.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketSurveyMap> ticketSurveyMapList = ticketSurveyMapRepository.findAll();
        assertThat(ticketSurveyMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
