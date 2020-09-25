package com.afreecar.survey.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.afreecar.survey.web.rest.TestUtil;

public class SurveyQuestionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyQuestion.class);
        SurveyQuestion surveyQuestion1 = new SurveyQuestion();
        surveyQuestion1.setId(1L);
        SurveyQuestion surveyQuestion2 = new SurveyQuestion();
        surveyQuestion2.setId(surveyQuestion1.getId());
        assertThat(surveyQuestion1).isEqualTo(surveyQuestion2);
        surveyQuestion2.setId(2L);
        assertThat(surveyQuestion1).isNotEqualTo(surveyQuestion2);
        surveyQuestion1.setId(null);
        assertThat(surveyQuestion1).isNotEqualTo(surveyQuestion2);
    }
}
