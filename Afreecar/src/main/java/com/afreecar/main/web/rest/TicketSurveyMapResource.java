package com.afreecar.main.web.rest;

import com.afreecar.main.domain.TicketSurveyMap;
import com.afreecar.main.repository.TicketSurveyMapRepository;
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
 * REST controller for managing {@link com.afreecar.main.domain.TicketSurveyMap}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TicketSurveyMapResource {

    private final Logger log = LoggerFactory.getLogger(TicketSurveyMapResource.class);

    private static final String ENTITY_NAME = "ticketSurveyMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketSurveyMapRepository ticketSurveyMapRepository;

    public TicketSurveyMapResource(TicketSurveyMapRepository ticketSurveyMapRepository) {
        this.ticketSurveyMapRepository = ticketSurveyMapRepository;
    }

    /**
     * {@code POST  /ticket-survey-maps} : Create a new ticketSurveyMap.
     *
     * @param ticketSurveyMap the ticketSurveyMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketSurveyMap, or with status {@code 400 (Bad Request)} if the ticketSurveyMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-survey-maps")
    public ResponseEntity<TicketSurveyMap> createTicketSurveyMap(@Valid @RequestBody TicketSurveyMap ticketSurveyMap) throws URISyntaxException {
        log.debug("REST request to save TicketSurveyMap : {}", ticketSurveyMap);
        if (ticketSurveyMap.getId() != null) {
            throw new BadRequestAlertException("A new ticketSurveyMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketSurveyMap result = ticketSurveyMapRepository.save(ticketSurveyMap);
        return ResponseEntity.created(new URI("/api/ticket-survey-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-survey-maps} : Updates an existing ticketSurveyMap.
     *
     * @param ticketSurveyMap the ticketSurveyMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketSurveyMap,
     * or with status {@code 400 (Bad Request)} if the ticketSurveyMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketSurveyMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-survey-maps")
    public ResponseEntity<TicketSurveyMap> updateTicketSurveyMap(@Valid @RequestBody TicketSurveyMap ticketSurveyMap) throws URISyntaxException {
        log.debug("REST request to update TicketSurveyMap : {}", ticketSurveyMap);
        if (ticketSurveyMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketSurveyMap result = ticketSurveyMapRepository.save(ticketSurveyMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketSurveyMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ticket-survey-maps} : get all the ticketSurveyMaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketSurveyMaps in body.
     */
    @GetMapping("/ticket-survey-maps")
    public List<TicketSurveyMap> getAllTicketSurveyMaps() {
        log.debug("REST request to get all TicketSurveyMaps");
        return ticketSurveyMapRepository.findAll();
    }

    /**
     * {@code GET  /ticket-survey-maps/:id} : get the "id" ticketSurveyMap.
     *
     * @param id the id of the ticketSurveyMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketSurveyMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-survey-maps/{id}")
    public ResponseEntity<TicketSurveyMap> getTicketSurveyMap(@PathVariable Long id) {
        log.debug("REST request to get TicketSurveyMap : {}", id);
        Optional<TicketSurveyMap> ticketSurveyMap = ticketSurveyMapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ticketSurveyMap);
    }

    /**
     * {@code DELETE  /ticket-survey-maps/:id} : delete the "id" ticketSurveyMap.
     *
     * @param id the id of the ticketSurveyMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-survey-maps/{id}")
    public ResponseEntity<Void> deleteTicketSurveyMap(@PathVariable Long id) {
        log.debug("REST request to delete TicketSurveyMap : {}", id);
        ticketSurveyMapRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
