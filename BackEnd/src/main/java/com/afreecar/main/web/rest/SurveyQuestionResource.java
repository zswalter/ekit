package com.afreecar.main.web.rest;

import com.afreecar.main.domain.SurveyQuestion;
import com.afreecar.main.repository.SurveyQuestionRepository;
import com.afreecar.main.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.afreecar.main.domain.SurveyQuestion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SurveyQuestionResource {

    private final Logger log = LoggerFactory.getLogger(SurveyQuestionResource.class);

    private static final String ENTITY_NAME = "surveyQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveyQuestionRepository surveyQuestionRepository;

    public SurveyQuestionResource(SurveyQuestionRepository surveyQuestionRepository) {
        this.surveyQuestionRepository = surveyQuestionRepository;
    }

    /**
     * {@code POST  /survey-questions} : Create a new surveyQuestion.
     *
     * @param surveyQuestion the surveyQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveyQuestion, or with status {@code 400 (Bad Request)} if the surveyQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/survey-questions")
    public ResponseEntity<SurveyQuestion> createSurveyQuestion(@RequestBody SurveyQuestion surveyQuestion) throws URISyntaxException {
        log.debug("REST request to save SurveyQuestion : {}", surveyQuestion);
        if (surveyQuestion.getId() != null) {
            throw new BadRequestAlertException("A new surveyQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyQuestion result = surveyQuestionRepository.save(surveyQuestion);
        return ResponseEntity.created(new URI("/api/survey-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /survey-questions} : Updates an existing surveyQuestion.
     *
     * @param surveyQuestion the surveyQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyQuestion,
     * or with status {@code 400 (Bad Request)} if the surveyQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveyQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/survey-questions")
    public ResponseEntity<SurveyQuestion> updateSurveyQuestion(@RequestBody SurveyQuestion surveyQuestion) throws URISyntaxException {
        log.debug("REST request to update SurveyQuestion : {}", surveyQuestion);
        if (surveyQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SurveyQuestion result = surveyQuestionRepository.save(surveyQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /survey-questions} : get all the surveyQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveyQuestions in body.
     */
    @GetMapping("/survey-questions")
    public List<SurveyQuestion> getAllSurveyQuestions() {
        log.debug("REST request to get all SurveyQuestions");
        return surveyQuestionRepository.findAll();
    }

    /**
     * {@code GET  /survey-questions/:id} : get the "id" surveyQuestion.
     *
     * @param id the id of the surveyQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveyQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/survey-questions/{id}")
    public ResponseEntity<SurveyQuestion> getSurveyQuestion(@PathVariable Long id) {
        log.debug("REST request to get SurveyQuestion : {}", id);
        Optional<SurveyQuestion> surveyQuestion = surveyQuestionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(surveyQuestion);
    }

    /**
     * {@code DELETE  /survey-questions/:id} : delete the "id" surveyQuestion.
     *
     * @param id the id of the surveyQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/survey-questions/{id}")
    public ResponseEntity<Void> deleteSurveyQuestion(@PathVariable Long id) {
        log.debug("REST request to delete SurveyQuestion : {}", id);
        surveyQuestionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
