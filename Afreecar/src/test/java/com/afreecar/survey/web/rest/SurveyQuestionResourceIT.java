package com.afreecar.survey.web.rest;

import com.afreecar.survey.AfreecarApp;
import com.afreecar.survey.domain.SurveyQuestion;
import com.afreecar.survey.repository.SurveyQuestionRepository;

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
 * Integration tests for the {@link SurveyQuestionResource} REST controller.
 */
@SpringBootTest(classes = AfreecarApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SurveyQuestionResourceIT {

    private static final Integer DEFAULT_ID_SURVEY_QUESTION = 1;
    private static final Integer UPDATED_ID_SURVEY_QUESTION = 2;

    private static final String DEFAULT_QUESTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyQuestionMockMvc;

    private SurveyQuestion surveyQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyQuestion createEntity(EntityManager em) {
        SurveyQuestion surveyQuestion = new SurveyQuestion()
            .idSurveyQuestion(DEFAULT_ID_SURVEY_QUESTION)
            .questionText(DEFAULT_QUESTION_TEXT)
            .isActive(DEFAULT_IS_ACTIVE);
        return surveyQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyQuestion createUpdatedEntity(EntityManager em) {
        SurveyQuestion surveyQuestion = new SurveyQuestion()
            .idSurveyQuestion(UPDATED_ID_SURVEY_QUESTION)
            .questionText(UPDATED_QUESTION_TEXT)
            .isActive(UPDATED_IS_ACTIVE);
        return surveyQuestion;
    }

    @BeforeEach
    public void initTest() {
        surveyQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSurveyQuestion() throws Exception {
        int databaseSizeBeforeCreate = surveyQuestionRepository.findAll().size();
        // Create the SurveyQuestion
        restSurveyQuestionMockMvc.perform(post("/api/survey-questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyQuestion)))
            .andExpect(status().isCreated());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        SurveyQuestion testSurveyQuestion = surveyQuestionList.get(surveyQuestionList.size() - 1);
        assertThat(testSurveyQuestion.getIdSurveyQuestion()).isEqualTo(DEFAULT_ID_SURVEY_QUESTION);
        assertThat(testSurveyQuestion.getQuestionText()).isEqualTo(DEFAULT_QUESTION_TEXT);
        assertThat(testSurveyQuestion.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createSurveyQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = surveyQuestionRepository.findAll().size();

        // Create the SurveyQuestion with an existing ID
        surveyQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyQuestionMockMvc.perform(post("/api/survey-questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdSurveyQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyQuestionRepository.findAll().size();
        // set the field null
        surveyQuestion.setIdSurveyQuestion(null);

        // Create the SurveyQuestion, which fails.


        restSurveyQuestionMockMvc.perform(post("/api/survey-questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyQuestion)))
            .andExpect(status().isBadRequest());

        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyQuestionRepository.findAll().size();
        // set the field null
        surveyQuestion.setQuestionText(null);

        // Create the SurveyQuestion, which fails.


        restSurveyQuestionMockMvc.perform(post("/api/survey-questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyQuestion)))
            .andExpect(status().isBadRequest());

        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSurveyQuestions() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        // Get all the surveyQuestionList
        restSurveyQuestionMockMvc.perform(get("/api/survey-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveyQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSurveyQuestion").value(hasItem(DEFAULT_ID_SURVEY_QUESTION)))
            .andExpect(jsonPath("$.[*].questionText").value(hasItem(DEFAULT_QUESTION_TEXT)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSurveyQuestion() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        // Get the surveyQuestion
        restSurveyQuestionMockMvc.perform(get("/api/survey-questions/{id}", surveyQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveyQuestion.getId().intValue()))
            .andExpect(jsonPath("$.idSurveyQuestion").value(DEFAULT_ID_SURVEY_QUESTION))
            .andExpect(jsonPath("$.questionText").value(DEFAULT_QUESTION_TEXT))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSurveyQuestion() throws Exception {
        // Get the surveyQuestion
        restSurveyQuestionMockMvc.perform(get("/api/survey-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSurveyQuestion() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();

        // Update the surveyQuestion
        SurveyQuestion updatedSurveyQuestion = surveyQuestionRepository.findById(surveyQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedSurveyQuestion are not directly saved in db
        em.detach(updatedSurveyQuestion);
        updatedSurveyQuestion
            .idSurveyQuestion(UPDATED_ID_SURVEY_QUESTION)
            .questionText(UPDATED_QUESTION_TEXT)
            .isActive(UPDATED_IS_ACTIVE);

        restSurveyQuestionMockMvc.perform(put("/api/survey-questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSurveyQuestion)))
            .andExpect(status().isOk());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
        SurveyQuestion testSurveyQuestion = surveyQuestionList.get(surveyQuestionList.size() - 1);
        assertThat(testSurveyQuestion.getIdSurveyQuestion()).isEqualTo(UPDATED_ID_SURVEY_QUESTION);
        assertThat(testSurveyQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testSurveyQuestion.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingSurveyQuestion() throws Exception {
        int databaseSizeBeforeUpdate = surveyQuestionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyQuestionMockMvc.perform(put("/api/survey-questions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the SurveyQuestion in the database
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSurveyQuestion() throws Exception {
        // Initialize the database
        surveyQuestionRepository.saveAndFlush(surveyQuestion);

        int databaseSizeBeforeDelete = surveyQuestionRepository.findAll().size();

        // Delete the surveyQuestion
        restSurveyQuestionMockMvc.perform(delete("/api/survey-questions/{id}", surveyQuestion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SurveyQuestion> surveyQuestionList = surveyQuestionRepository.findAll();
        assertThat(surveyQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
