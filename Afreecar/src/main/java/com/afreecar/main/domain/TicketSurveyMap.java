package com.afreecar.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TicketSurveyMap.
 */
@Entity
@Table(name = "ticket_survey_map")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketSurveyMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_ticket", nullable = false)
    private Integer idTicket;

    @NotNull
    @Column(name = "id_survey_answer", nullable = false)
    private Integer idSurveyAnswer;

    @ManyToOne
    @JsonIgnoreProperties(value = "ticketSurveyMaps", allowSetters = true)
    private SurveyAnswer idSurveyAnswer;

    @ManyToOne
    @JsonIgnoreProperties(value = "ticketSurveyMaps", allowSetters = true)
    private Ticket idTicket;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public TicketSurveyMap idTicket(Integer idTicket) {
        this.idTicket = idTicket;
        return this;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Integer getIdSurveyAnswer() {
        return idSurveyAnswer;
    }

    public TicketSurveyMap idSurveyAnswer(Integer idSurveyAnswer) {
        this.idSurveyAnswer = idSurveyAnswer;
        return this;
    }

    public void setIdSurveyAnswer(Integer idSurveyAnswer) {
        this.idSurveyAnswer = idSurveyAnswer;
    }

    public SurveyAnswer getIdSurveyAnswer() {
        return idSurveyAnswer;
    }

    public TicketSurveyMap idSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.idSurveyAnswer = surveyAnswer;
        return this;
    }

    public void setIdSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.idSurveyAnswer = surveyAnswer;
    }

    public Ticket getIdTicket() {
        return idTicket;
    }

    public TicketSurveyMap idTicket(Ticket ticket) {
        this.idTicket = ticket;
        return this;
    }

    public void setIdTicket(Ticket ticket) {
        this.idTicket = ticket;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketSurveyMap)) {
            return false;
        }
        return id != null && id.equals(((TicketSurveyMap) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketSurveyMap{" +
            "id=" + getId() +
            ", idTicket=" + getIdTicket() +
            ", idSurveyAnswer=" + getIdSurveyAnswer() +
            "}";
    }
}
