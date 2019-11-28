package com.jio.tmsapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.jio.tmsapp.domain.enumeration.StatusEnum;

/**
 * A LoadOrder.
 */
@Entity
@Table(name = "load_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "loadorder")
public class LoadOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "order_nuber")
    private String orderNuber;

    @Column(name = "description")
    private String description;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "bol")
    private String bol;

    @Column(name = "pickup")
    private LocalDate pickup;

    @Column(name = "jhi_drop")
    private LocalDate drop;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "current_location")
    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "detention")
    private Long detention;

    @Column(name = "chasis_in_time")
    private Instant chasisInTime;

    @Lob
    @Column(name = "pod")
    private byte[] pod;

    @Column(name = "pod_content_type")
    private String podContentType;

    @Column(name = "hazmat")
    private Boolean hazmat;

    @Column(name = "recieved_by")
    private String recievedBy;

    @OneToMany(mappedBy = "mainBooking")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookingItem> bookingItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNuber() {
        return orderNuber;
    }

    public LoadOrder orderNuber(String orderNuber) {
        this.orderNuber = orderNuber;
        return this;
    }

    public void setOrderNuber(String orderNuber) {
        this.orderNuber = orderNuber;
    }

    public String getDescription() {
        return description;
    }

    public LoadOrder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public LoadOrder shipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
        return this;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getBol() {
        return bol;
    }

    public LoadOrder bol(String bol) {
        this.bol = bol;
        return this;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }

    public LocalDate getPickup() {
        return pickup;
    }

    public LoadOrder pickup(LocalDate pickup) {
        this.pickup = pickup;
        return this;
    }

    public void setPickup(LocalDate pickup) {
        this.pickup = pickup;
    }

    public LocalDate getDrop() {
        return drop;
    }

    public LoadOrder drop(LocalDate drop) {
        this.drop = drop;
        return this;
    }

    public void setDrop(LocalDate drop) {
        this.drop = drop;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public LoadOrder pickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
        return this;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public LoadOrder dropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
        return this;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public LoadOrder currentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public LoadOrder status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getDetention() {
        return detention;
    }

    public LoadOrder detention(Long detention) {
        this.detention = detention;
        return this;
    }

    public void setDetention(Long detention) {
        this.detention = detention;
    }

    public Instant getChasisInTime() {
        return chasisInTime;
    }

    public LoadOrder chasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
        return this;
    }

    public void setChasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
    }

    public byte[] getPod() {
        return pod;
    }

    public LoadOrder pod(byte[] pod) {
        this.pod = pod;
        return this;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public LoadOrder podContentType(String podContentType) {
        this.podContentType = podContentType;
        return this;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public Boolean isHazmat() {
        return hazmat;
    }

    public LoadOrder hazmat(Boolean hazmat) {
        this.hazmat = hazmat;
        return this;
    }

    public void setHazmat(Boolean hazmat) {
        this.hazmat = hazmat;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public LoadOrder recievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
        return this;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public Set<BookingItem> getBookingItems() {
        return bookingItems;
    }

    public LoadOrder bookingItems(Set<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
        return this;
    }

    public LoadOrder addBookingItem(BookingItem bookingItem) {
        this.bookingItems.add(bookingItem);
        bookingItem.setMainBooking(this);
        return this;
    }

    public LoadOrder removeBookingItem(BookingItem bookingItem) {
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

    public LoadOrder customer(Customer customer) {
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
        if (!(o instanceof LoadOrder)) {
            return false;
        }
        return id != null && id.equals(((LoadOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LoadOrder{" +
            "id=" + getId() +
            ", orderNuber='" + getOrderNuber() + "'" +
            ", description='" + getDescription() + "'" +
            ", shipmentNumber='" + getShipmentNumber() + "'" +
            ", bol='" + getBol() + "'" +
            ", pickup='" + getPickup() + "'" +
            ", drop='" + getDrop() + "'" +
            ", pickupLocation='" + getPickupLocation() + "'" +
            ", dropLocation='" + getDropLocation() + "'" +
            ", currentLocation='" + getCurrentLocation() + "'" +
            ", status='" + getStatus() + "'" +
            ", detention=" + getDetention() +
            ", chasisInTime='" + getChasisInTime() + "'" +
            ", pod='" + getPod() + "'" +
            ", podContentType='" + getPodContentType() + "'" +
            ", hazmat='" + isHazmat() + "'" +
            ", recievedBy='" + getRecievedBy() + "'" +
            "}";
    }
}
