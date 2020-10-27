package com.afreecar.main.web.rest;

import com.afreecar.main.domain.TicketComment;
import com.afreecar.main.repository.TicketCommentRepository;
import com.afreecar.main.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.afreecar.main.domain.TicketComment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TicketCommentResource {

    private final Logger log = LoggerFactory.getLogger(TicketCommentResource.class);

    private static final String ENTITY_NAME = "ticketComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketCommentRepository ticketCommentRepository;

    public TicketCommentResource(TicketCommentRepository ticketCommentRepository) {
        this.ticketCommentRepository = ticketCommentRepository;
    }

    /**
     * {@code POST  /ticket-comments} : Create a new ticketComment.
     *
     * @param ticketComment the ticketComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketComment, or with status {@code 400 (Bad Request)} if the ticketComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-comments")
    public ResponseEntity<TicketComment> createTicketComment(@Valid @RequestBody TicketComment ticketComment) throws URISyntaxException {
        log.debug("REST request to save TicketComment : {}", ticketComment);
        if (ticketComment.getId() != null) {
            throw new BadRequestAlertException("A new ticketComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketComment result = ticketCommentRepository.save(ticketComment);
        return ResponseEntity.created(new URI("/api/ticket-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-comments} : Updates an existing ticketComment.
     *
     * @param ticketComment the ticketComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketComment,
     * or with status {@code 400 (Bad Request)} if the ticketComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-comments")
    public ResponseEntity<TicketComment> updateTicketComment(@Valid @RequestBody TicketComment ticketComment) throws URISyntaxException {
        log.debug("REST request to update TicketComment : {}", ticketComment);
        if (ticketComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketComment result = ticketCommentRepository.save(ticketComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ticket-comments} : get all the ticketComments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketComments in body.
     */
    @GetMapping("/ticket-comments")
    public List<TicketComment> getAllTicketComments() {
        log.debug("REST request to get all TicketComments");
        return ticketCommentRepository.findAll();
    }

    /**
     * {@code GET  /ticket-comments/:id} : get the "id" ticketComment.
     *
     * @param id the id of the ticketComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-comments/{id}")
    public ResponseEntity<TicketComment> getTicketComment(@PathVariable Long id) {
        log.debug("REST request to get TicketComment : {}", id);
        Optional<TicketComment> ticketComment = ticketCommentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ticketComment);
    }

    /**
     * {@code DELETE  /ticket-comments/:id} : delete the "id" ticketComment.
     *
     * @param id the id of the ticketComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-comments/{id}")
    public ResponseEntity<Void> deleteTicketComment(@PathVariable Long id) {
        log.debug("REST request to delete TicketComment : {}", id);
        ticketCommentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
