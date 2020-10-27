package com.afreecar.main.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SurveyQuestion.
 */
@Entity
@Table(name = "survey_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SurveyQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_survey_question", nullable = false, unique = true)
    private Integer idSurveyQuestion;

    @NotNull
    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSurveyQuestion() {
        return idSurveyQuestion;
    }

    public SurveyQuestion idSurveyQuestion(Integer idSurveyQuestion) {
        this.idSurveyQuestion = idSurveyQuestion;
        return this;
    }

    public void setIdSurveyQuestion(Integer idSurveyQuestion) {
        this.idSurveyQuestion = idSurveyQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public SurveyQuestion questionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public SurveyQuestion isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyQuestion)) {
            return false;
        }
        return id != null && id.equals(((SurveyQuestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyQuestion{" +
            "id=" + getId() +
            ", idSurveyQuestion=" + getIdSurveyQuestion() +
            ", questionText='" + getQuestionText() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
