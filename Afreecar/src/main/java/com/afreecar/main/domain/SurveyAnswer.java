package com.afreecar.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotNull
    @Column(name = "id_survey_answer", nullable = false)
    private Integer idSurveyAnswer;

    @NotNull
    @Column(name = "id_question", nullable = false)
    private Integer idQuestion;

    @NotNull
    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "surveyAnswers", allowSetters = true)
    private SurveyQuestion idSurveyQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSurveyAnswer() {
        return idSurveyAnswer;
    }

    public SurveyAnswer idSurveyAnswer(Integer idSurveyAnswer) {
        this.idSurveyAnswer = idSurveyAnswer;
        return this;
    }

    public void setIdSurveyAnswer(Integer idSurveyAnswer) {
        this.idSurveyAnswer = idSurveyAnswer;
    }

    public Integer getIdQuestion() {
        return idQuestion;
    }

    public SurveyAnswer idQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
        return this;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
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

    public SurveyQuestion getIdSurveyQuestion() {
        return idSurveyQuestion;
    }

    public SurveyAnswer idSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.idSurveyQuestion = surveyQuestion;
        return this;
    }

    public void setIdSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.idSurveyQuestion = surveyQuestion;
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
            ", idSurveyAnswer=" + getIdSurveyAnswer() +
            ", idQuestion=" + getIdQuestion() +
            ", answerText='" + getAnswerText() + "'" +
            "}";
    }
}
