package com.afreecar.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SurveyAnswer.
 */
@Entity
@Table(name = "survey_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SurveyAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JsonIgnoreProperties(value = "surveyAnswers", allowSetters = true)
    private SurveyQuestion surveyQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public SurveyAnswer answerText(String answerText) {
        this.answerText = answerText;
        return this;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public SurveyQuestion getSurveyQuestion() {
        return surveyQuestion;
    }

    public SurveyAnswer surveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
        return this;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyAnswer)) {
            return false;
        }
        return id != null && id.equals(((SurveyAnswer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyAnswer{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            "}";
    }
}
