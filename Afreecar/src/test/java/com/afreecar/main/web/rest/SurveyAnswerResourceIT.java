package com.afreecar.main.web.rest;

import com.afreecar.main.AfreecarApp;
import com.afreecar.main.domain.SurveyAnswer;
import com.afreecar.main.domain.SurveyQuestion;
import com.afreecar.main.repository.SurveyAnswerRepository;

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
 * Integration tests for the {@link SurveyAnswerResource} REST controller.
 */
@SpringBootTest(classes = AfreecarApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SurveyAnswerResourceIT {

    private static final Integer DEFAULT_ID_SURVEY_ANSWER = 1;
    private static final Integer UPDATED_ID_SURVEY_ANSWER = 2;

    private static final Integer DEFAULT_ID_QUESTION = 1;
    private static final Integer UPDATED_ID_QUESTION = 2;

    private static final String DEFAULT_ANSWER_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_TEXT = "BBBBBBBBBB";

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSurveyAnswerMockMvc;

    private SurveyAnswer surveyAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyAnswer createEntity(EntityManager em) {
        SurveyAnswer surveyAnswer = new SurveyAnswer()
            .idSurveyAnswer(DEFAULT_ID_SURVEY_ANSWER)
            .idQuestion(DEFAULT_ID_QUESTION)
            .answerText(DEFAULT_ANSWER_TEXT);
        // Add required entity
        SurveyQuestion surveyQuestion;
        if (TestUtil.findAll(em, SurveyQuestion.class).isEmpty()) {
            surveyQuestion = SurveyQuestionResourceIT.createEntity(em);
            em.persist(surveyQuestion);
            em.flush();
        } else {
            surveyQuestion = TestUtil.findAll(em, SurveyQuestion.class).get(0);
        }
        surveyAnswer.setIdSurveyQuestion(surveyQuestion);
        return surveyAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyAnswer createUpdatedEntity(EntityManager em) {
        SurveyAnswer surveyAnswer = new SurveyAnswer()
            .idSurveyAnswer(UPDATED_ID_SURVEY_ANSWER)
            .idQuestion(UPDATED_ID_QUESTION)
            .answerText(UPDATED_ANSWER_TEXT);
        // Add required entity
        SurveyQuestion surveyQuestion;
        if (TestUtil.findAll(em, SurveyQuestion.class).isEmpty()) {
            surveyQuestion = SurveyQuestionResourceIT.createUpdatedEntity(em);
            em.persist(surveyQuestion);
            em.flush();
        } else {
            surveyQuestion = TestUtil.findAll(em, SurveyQuestion.class).get(0);
        }
        surveyAnswer.setIdSurveyQuestion(surveyQuestion);
        return surveyAnswer;
    }

    @BeforeEach
    public void initTest() {
        surveyAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSurveyAnswer() throws Exception {
        int databaseSizeBeforeCreate = surveyAnswerRepository.findAll().size();
        // Create the SurveyAnswer
        restSurveyAnswerMockMvc.perform(post("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyAnswer)))
            .andExpect(status().isCreated());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        SurveyAnswer testSurveyAnswer = surveyAnswerList.get(surveyAnswerList.size() - 1);
        assertThat(testSurveyAnswer.getIdSurveyAnswer()).isEqualTo(DEFAULT_ID_SURVEY_ANSWER);
        assertThat(testSurveyAnswer.getIdQuestion()).isEqualTo(DEFAULT_ID_QUESTION);
        assertThat(testSurveyAnswer.getAnswerText()).isEqualTo(DEFAULT_ANSWER_TEXT);
    }

    @Test
    @Transactional
    public void createSurveyAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = surveyAnswerRepository.findAll().size();

        // Create the SurveyAnswer with an existing ID
        surveyAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyAnswerMockMvc.perform(post("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdSurveyAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyAnswerRepository.findAll().size();
        // set the field null
        surveyAnswer.setIdSurveyAnswer(null);

        // Create the SurveyAnswer, which fails.


        restSurveyAnswerMockMvc.perform(post("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyAnswer)))
            .andExpect(status().isBadRequest());

        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyAnswerRepository.findAll().size();
        // set the field null
        surveyAnswer.setIdQuestion(null);

        // Create the SurveyAnswer, which fails.


        restSurveyAnswerMockMvc.perform(post("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyAnswer)))
            .andExpect(status().isBadRequest());

        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnswerTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = surveyAnswerRepository.findAll().size();
        // set the field null
        surveyAnswer.setAnswerText(null);

        // Create the SurveyAnswer, which fails.


        restSurveyAnswerMockMvc.perform(post("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyAnswer)))
            .andExpect(status().isBadRequest());

        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSurveyAnswers() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        // Get all the surveyAnswerList
        restSurveyAnswerMockMvc.perform(get("/api/survey-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveyAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSurveyAnswer").value(hasItem(DEFAULT_ID_SURVEY_ANSWER)))
            .andExpect(jsonPath("$.[*].idQuestion").value(hasItem(DEFAULT_ID_QUESTION)))
            .andExpect(jsonPath("$.[*].answerText").value(hasItem(DEFAULT_ANSWER_TEXT)));
    }
    
    @Test
    @Transactional
    public void getSurveyAnswer() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        // Get the surveyAnswer
        restSurveyAnswerMockMvc.perform(get("/api/survey-answers/{id}", surveyAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(surveyAnswer.getId().intValue()))
            .andExpect(jsonPath("$.idSurveyAnswer").value(DEFAULT_ID_SURVEY_ANSWER))
            .andExpect(jsonPath("$.idQuestion").value(DEFAULT_ID_QUESTION))
            .andExpect(jsonPath("$.answerText").value(DEFAULT_ANSWER_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingSurveyAnswer() throws Exception {
        // Get the surveyAnswer
        restSurveyAnswerMockMvc.perform(get("/api/survey-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSurveyAnswer() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();

        // Update the surveyAnswer
        SurveyAnswer updatedSurveyAnswer = surveyAnswerRepository.findById(surveyAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedSurveyAnswer are not directly saved in db
        em.detach(updatedSurveyAnswer);
        updatedSurveyAnswer
            .idSurveyAnswer(UPDATED_ID_SURVEY_ANSWER)
            .idQuestion(UPDATED_ID_QUESTION)
            .answerText(UPDATED_ANSWER_TEXT);

        restSurveyAnswerMockMvc.perform(put("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSurveyAnswer)))
            .andExpect(status().isOk());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
        SurveyAnswer testSurveyAnswer = surveyAnswerList.get(surveyAnswerList.size() - 1);
        assertThat(testSurveyAnswer.getIdSurveyAnswer()).isEqualTo(UPDATED_ID_SURVEY_ANSWER);
        assertThat(testSurveyAnswer.getIdQuestion()).isEqualTo(UPDATED_ID_QUESTION);
        assertThat(testSurveyAnswer.getAnswerText()).isEqualTo(UPDATED_ANSWER_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingSurveyAnswer() throws Exception {
        int databaseSizeBeforeUpdate = surveyAnswerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyAnswerMockMvc.perform(put("/api/survey-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(surveyAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the SurveyAnswer in the database
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSurveyAnswer() throws Exception {
        // Initialize the database
        surveyAnswerRepository.saveAndFlush(surveyAnswer);

        int databaseSizeBeforeDelete = surveyAnswerRepository.findAll().size();

        // Delete the surveyAnswer
        restSurveyAnswerMockMvc.perform(delete("/api/survey-answers/{id}", surveyAnswer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findAll();
        assertThat(surveyAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
