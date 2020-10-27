package com.afreecar.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A TicketComment.
 */
@Entity
@Table(name = "ticket_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_comment", nullable = false, unique = true)
    private Integer idComment;

    @NotNull
    @Column(name = "id_ticket", nullable = false)
    private Integer idTicket;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ticketComments", allowSetters = true)
    private Ticket ticketID;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdComment() {
        return idComment;
    }

    public TicketComment idComment(Integer idComment) {
        this.idComment = idComment;
        return this;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public TicketComment idTicket(Integer idTicket) {
        this.idTicket = idTicket;
        return this;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public TicketComment idUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public TicketComment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public TicketComment created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Ticket getTicketID() {
        return ticketID;
    }

    public TicketComment ticketID(Ticket ticket) {
        this.ticketID = ticket;
        return this;
    }

    public void setTicketID(Ticket ticket) {
        this.ticketID = ticket;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketComment)) {
            return false;
        }
        return id != null && id.equals(((TicketComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketComment{" +
            "id=" + getId() +
            ", idComment=" + getIdComment() +
            ", idTicket=" + getIdTicket() +
            ", idUser=" + getIdUser() +
            ", content='" + getContent() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
