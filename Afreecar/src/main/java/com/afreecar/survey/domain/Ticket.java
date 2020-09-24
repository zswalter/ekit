package com.afreecar.survey.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_ticket", nullable = false, unique = true)
    private Integer idTicket;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "closed")
    private ZonedDateTime closed;

    @Column(name = "close_by_user_id")
    private Integer closeByUserID;

    @Column(name = "product_id")
    private Integer productID;

    @OneToOne
    @JoinColumn(unique = true)
    private Product productId;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User userID;

    @OneToOne
    @JoinColumn(unique = true)
    private User closedByUserID;

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

    public Ticket idTicket(Integer idTicket) {
        this.idTicket = idTicket;
        return this;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Ticket idUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Ticket created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getClosed() {
        return closed;
    }

    public Ticket closed(ZonedDateTime closed) {
        this.closed = closed;
        return this;
    }

    public void setClosed(ZonedDateTime closed) {
        this.closed = closed;
    }

    public Integer getCloseByUserID() {
        return closeByUserID;
    }

    public Ticket closeByUserID(Integer closeByUserID) {
        this.closeByUserID = closeByUserID;
        return this;
    }

    public void setCloseByUserID(Integer closeByUserID) {
        this.closeByUserID = closeByUserID;
    }

    public Integer getProductID() {
        return productID;
    }

    public Ticket productID(Integer productID) {
        this.productID = productID;
        return this;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Product getProductId() {
        return productId;
    }

    public Ticket productId(Product product) {
        this.productId = product;
        return this;
    }

    public void setProductId(Product product) {
        this.productId = product;
    }

    public User getUserID() {
        return userID;
    }

    public Ticket userID(User user) {
        this.userID = user;
        return this;
    }

    public void setUserID(User user) {
        this.userID = user;
    }

    public User getClosedByUserID() {
        return closedByUserID;
    }

    public Ticket closedByUserID(User user) {
        this.closedByUserID = user;
        return this;
    }

    public void setClosedByUserID(User user) {
        this.closedByUserID = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", idTicket=" + getIdTicket() +
            ", idUser=" + getIdUser() +
            ", created='" + getCreated() + "'" +
            ", closed='" + getClosed() + "'" +
            ", closeByUserID=" + getCloseByUserID() +
            ", productID=" + getProductID() +
            "}";
    }
}
