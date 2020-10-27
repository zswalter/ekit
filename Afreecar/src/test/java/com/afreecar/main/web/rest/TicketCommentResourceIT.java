package com.afreecar.main.web.rest;

import com.afreecar.main.AfreecarApp;
import com.afreecar.main.domain.TicketComment;
import com.afreecar.main.domain.Ticket;
import com.afreecar.main.repository.TicketCommentRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.afreecar.main.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TicketCommentResource} REST controller.
 */
@SpringBootTest(classes = AfreecarApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketCommentResourceIT {

    private static final Integer DEFAULT_ID_COMMENT = 1;
    private static final Integer UPDATED_ID_COMMENT = 2;

    private static final Integer DEFAULT_ID_TICKET = 1;
    private static final Integer UPDATED_ID_TICKET = 2;

    private static final Integer DEFAULT_ID_USER = 1;
    private static final Integer UPDATED_ID_USER = 2;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketCommentMockMvc;

    private TicketComment ticketComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketComment createEntity(EntityManager em) {
        TicketComment ticketComment = new TicketComment()
            .idComment(DEFAULT_ID_COMMENT)
            .idTicket(DEFAULT_ID_TICKET)
            .idUser(DEFAULT_ID_USER)
            .content(DEFAULT_CONTENT)
            .created(DEFAULT_CREATED);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketComment.setTicketID(ticket);
        return ticketComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketComment createUpdatedEntity(EntityManager em) {
        TicketComment ticketComment = new TicketComment()
            .idComment(UPDATED_ID_COMMENT)
            .idTicket(UPDATED_ID_TICKET)
            .idUser(UPDATED_ID_USER)
            .content(UPDATED_CONTENT)
            .created(UPDATED_CREATED);
        // Add required entity
        Ticket ticket;
        if (TestUtil.findAll(em, Ticket.class).isEmpty()) {
            ticket = TicketResourceIT.createUpdatedEntity(em);
            em.persist(ticket);
            em.flush();
        } else {
            ticket = TestUtil.findAll(em, Ticket.class).get(0);
        }
        ticketComment.setTicketID(ticket);
        return ticketComment;
    }

    @BeforeEach
    public void initTest() {
        ticketComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketComment() throws Exception {
        int databaseSizeBeforeCreate = ticketCommentRepository.findAll().size();
        // Create the TicketComment
        restTicketCommentMockMvc.perform(post("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isCreated());

        // Validate the TicketComment in the database
        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeCreate + 1);
        TicketComment testTicketComment = ticketCommentList.get(ticketCommentList.size() - 1);
        assertThat(testTicketComment.getIdComment()).isEqualTo(DEFAULT_ID_COMMENT);
        assertThat(testTicketComment.getIdTicket()).isEqualTo(DEFAULT_ID_TICKET);
        assertThat(testTicketComment.getIdUser()).isEqualTo(DEFAULT_ID_USER);
        assertThat(testTicketComment.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTicketComment.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createTicketCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketCommentRepository.findAll().size();

        // Create the TicketComment with an existing ID
        ticketComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketCommentMockMvc.perform(post("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isBadRequest());

        // Validate the TicketComment in the database
        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketCommentRepository.findAll().size();
        // set the field null
        ticketComment.setIdComment(null);

        // Create the TicketComment, which fails.


        restTicketCommentMockMvc.perform(post("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isBadRequest());

        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdTicketIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketCommentRepository.findAll().size();
        // set the field null
        ticketComment.setIdTicket(null);

        // Create the TicketComment, which fails.


        restTicketCommentMockMvc.perform(post("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isBadRequest());

        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketCommentRepository.findAll().size();
        // set the field null
        ticketComment.setIdUser(null);

        // Create the TicketComment, which fails.


        restTicketCommentMockMvc.perform(post("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isBadRequest());

        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketCommentRepository.findAll().size();
        // set the field null
        ticketComment.setContent(null);

        // Create the TicketComment, which fails.


        restTicketCommentMockMvc.perform(post("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isBadRequest());

        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTicketComments() throws Exception {
        // Initialize the database
        ticketCommentRepository.saveAndFlush(ticketComment);

        // Get all the ticketCommentList
        restTicketCommentMockMvc.perform(get("/api/ticket-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].idComment").value(hasItem(DEFAULT_ID_COMMENT)))
            .andExpect(jsonPath("$.[*].idTicket").value(hasItem(DEFAULT_ID_TICKET)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }
    
    @Test
    @Transactional
    public void getTicketComment() throws Exception {
        // Initialize the database
        ticketCommentRepository.saveAndFlush(ticketComment);

        // Get the ticketComment
        restTicketCommentMockMvc.perform(get("/api/ticket-comments/{id}", ticketComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketComment.getId().intValue()))
            .andExpect(jsonPath("$.idComment").value(DEFAULT_ID_COMMENT))
            .andExpect(jsonPath("$.idTicket").value(DEFAULT_ID_TICKET))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }
    @Test
    @Transactional
    public void getNonExistingTicketComment() throws Exception {
        // Get the ticketComment
        restTicketCommentMockMvc.perform(get("/api/ticket-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketComment() throws Exception {
        // Initialize the database
        ticketCommentRepository.saveAndFlush(ticketComment);

        int databaseSizeBeforeUpdate = ticketCommentRepository.findAll().size();

        // Update the ticketComment
        TicketComment updatedTicketComment = ticketCommentRepository.findById(ticketComment.getId()).get();
        // Disconnect from session so that the updates on updatedTicketComment are not directly saved in db
        em.detach(updatedTicketComment);
        updatedTicketComment
            .idComment(UPDATED_ID_COMMENT)
            .idTicket(UPDATED_ID_TICKET)
            .idUser(UPDATED_ID_USER)
            .content(UPDATED_CONTENT)
            .created(UPDATED_CREATED);

        restTicketCommentMockMvc.perform(put("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTicketComment)))
            .andExpect(status().isOk());

        // Validate the TicketComment in the database
        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeUpdate);
        TicketComment testTicketComment = ticketCommentList.get(ticketCommentList.size() - 1);
        assertThat(testTicketComment.getIdComment()).isEqualTo(UPDATED_ID_COMMENT);
        assertThat(testTicketComment.getIdTicket()).isEqualTo(UPDATED_ID_TICKET);
        assertThat(testTicketComment.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testTicketComment.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTicketComment.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketComment() throws Exception {
        int databaseSizeBeforeUpdate = ticketCommentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketCommentMockMvc.perform(put("/api/ticket-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ticketComment)))
            .andExpect(status().isBadRequest());

        // Validate the TicketComment in the database
        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTicketComment() throws Exception {
        // Initialize the database
        ticketCommentRepository.saveAndFlush(ticketComment);

        int databaseSizeBeforeDelete = ticketCommentRepository.findAll().size();

        // Delete the ticketComment
        restTicketCommentMockMvc.perform(delete("/api/ticket-comments/{id}", ticketComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketComment> ticketCommentList = ticketCommentRepository.findAll();
        assertThat(ticketCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
