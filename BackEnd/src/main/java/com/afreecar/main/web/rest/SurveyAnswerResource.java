package com.afreecar.main.web.rest;

import com.afreecar.main.domain.SurveyAnswer;
import com.afreecar.main.repository.SurveyAnswerRepository;
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
 * REST controller for managing {@link com.afreecar.main.domain.SurveyAnswer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SurveyAnswerResource {

    private final Logger log = LoggerFactory.getLogger(SurveyAnswerResource.class);

    private static final String ENTITY_NAME = "surveyAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveyAnswerRepository surveyAnswerRepository;

    public SurveyAnswerResource(SurveyAnswerRepository surveyAnswerRepository) {
        this.surveyAnswerRepository = surveyAnswerRepository;
    }

    /**
     * {@code POST  /survey-answers} : Create a new surveyAnswer.
     *
     * @param surveyAnswer the surveyAnswer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveyAnswer, or with status {@code 400 (Bad Request)} if the surveyAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/survey-answers")
    public ResponseEntity<SurveyAnswer> createSurveyAnswer(@RequestBody SurveyAnswer surveyAnswer) throws URISyntaxException {
        log.debug("REST request to save SurveyAnswer : {}", surveyAnswer);
        if (surveyAnswer.getId() != null) {
            throw new BadRequestAlertException("A new surveyAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyAnswer result = surveyAnswerRepository.save(surveyAnswer);
        return ResponseEntity.created(new URI("/api/survey-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /survey-answers} : Updates an existing surveyAnswer.
     *
     * @param surveyAnswer the surveyAnswer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveyAnswer,
     * or with status {@code 400 (Bad Request)} if the surveyAnswer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveyAnswer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/survey-answers")
    public ResponseEntity<SurveyAnswer> updateSurveyAnswer(@RequestBody SurveyAnswer surveyAnswer) throws URISyntaxException {
        log.debug("REST request to update SurveyAnswer : {}", surveyAnswer);
        if (surveyAnswer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SurveyAnswer result = surveyAnswerRepository.save(surveyAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, surveyAnswer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /survey-answers} : get all the surveyAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveyAnswers in body.
     */
    @GetMapping("/survey-answers")
    public List<SurveyAnswer> getAllSurveyAnswers() {
        log.debug("REST request to get all SurveyAnswers");
        return surveyAnswerRepository.findAll();
    }

    /**
     * {@code GET  /survey-answers/:id} : get the "id" surveyAnswer.
     *
     * @param id the id of the surveyAnswer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveyAnswer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/survey-answers/{id}")
    public ResponseEntity<SurveyAnswer> getSurveyAnswer(@PathVariable Long id) {
        log.debug("REST request to get SurveyAnswer : {}", id);
        Optional<SurveyAnswer> surveyAnswer = surveyAnswerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(surveyAnswer);
    }

    /**
     * {@code DELETE  /survey-answers/:id} : delete the "id" surveyAnswer.
     *
     * @param id the id of the surveyAnswer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/survey-answers/{id}")
    public ResponseEntity<Void> deleteSurveyAnswer(@PathVariable Long id) {
        log.debug("REST request to delete SurveyAnswer : {}", id);
        surveyAnswerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
