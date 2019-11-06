package com.jio.tmsapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jio.tmsapp.domain.enumeration.StatusEnum;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "load_nuber")
    private String loadNuber;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "bol")
    private String bol;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @OneToMany(mappedBy = "mainBooking")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookingItem> bookingItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Booking name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoadNuber() {
        return loadNuber;
    }

    public Booking loadNuber(String loadNuber) {
        this.loadNuber = loadNuber;
        return this;
    }

    public void setLoadNuber(String loadNuber) {
        this.loadNuber = loadNuber;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public Booking shipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
        return this;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getBol() {
        return bol;
    }

    public Booking bol(String bol) {
        this.bol = bol;
        return this;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Booking status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Set<BookingItem> getBookingItems() {
        return bookingItems;
    }

    public Booking bookingItems(Set<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
        return this;
    }

    public Booking addBookingItem(BookingItem bookingItem) {
        this.bookingItems.add(bookingItem);
        bookingItem.setMainBooking(this);
        return this;
    }

    public Booking removeBookingItem(BookingItem bookingItem) {
        this.bookingItems.remove(bookingItem);
        bookingItem.setMainBooking(null);
        return this;
    }

    public void setBookingItems(Set<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Booking customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", loadNuber='" + getLoadNuber() + "'" +
            ", shipmentNumber='" + getShipmentNumber() + "'" +
            ", bol='" + getBol() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
