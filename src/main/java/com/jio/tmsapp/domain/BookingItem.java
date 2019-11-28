package com.jio.tmsapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.jio.tmsapp.domain.enumeration.StatusEnum;

/**
 * A BookingItem.
 */
@Entity
@Table(name = "booking_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bookingitem")
public class BookingItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "pickup")
    private Instant pickup;

    @Column(name = "jhi_drop")
    private Instant drop;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

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

    @OneToMany(mappedBy = "bookingItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Equipment> equipment = new HashSet<>();

    @OneToMany(mappedBy = "bookingItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Driver> drivers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("bookingItems")
    private LoadOrder mainBooking;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public BookingItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getPickup() {
        return pickup;
    }

    public BookingItem pickup(Instant pickup) {
        this.pickup = pickup;
        return this;
    }

    public void setPickup(Instant pickup) {
        this.pickup = pickup;
    }

    public Instant getDrop() {
        return drop;
    }

    public BookingItem drop(Instant drop) {
        this.drop = drop;
        return this;
    }

    public void setDrop(Instant drop) {
        this.drop = drop;
    }

    public String getSource() {
        return source;
    }

    public BookingItem source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public BookingItem destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public BookingItem currentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public BookingItem status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getDetention() {
        return detention;
    }

    public BookingItem detention(Long detention) {
        this.detention = detention;
        return this;
    }

    public void setDetention(Long detention) {
        this.detention = detention;
    }

    public Instant getChasisInTime() {
        return chasisInTime;
    }

    public BookingItem chasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
        return this;
    }

    public void setChasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
    }

    public byte[] getPod() {
        return pod;
    }

    public BookingItem pod(byte[] pod) {
        this.pod = pod;
        return this;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public BookingItem podContentType(String podContentType) {
        this.podContentType = podContentType;
        return this;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public Boolean isHazmat() {
        return hazmat;
    }

    public BookingItem hazmat(Boolean hazmat) {
        this.hazmat = hazmat;
        return this;
    }

    public void setHazmat(Boolean hazmat) {
        this.hazmat = hazmat;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public BookingItem recievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
        return this;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public BookingItem equipment(Set<Equipment> equipment) {
        this.equipment = equipment;
        return this;
    }

    public BookingItem addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
        equipment.setBookingItem(this);
        return this;
    }

    public BookingItem removeEquipment(Equipment equipment) {
        this.equipment.remove(equipment);
        equipment.setBookingItem(null);
        return this;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public BookingItem drivers(Set<Driver> drivers) {
        this.drivers = drivers;
        return this;
    }

    public BookingItem addDriver(Driver driver) {
        this.drivers.add(driver);
        driver.setBookingItem(this);
        return this;
    }

    public BookingItem removeDriver(Driver driver) {
        this.drivers.remove(driver);
        driver.setBookingItem(null);
        return this;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public LoadOrder getMainBooking() {
        return mainBooking;
    }

    public BookingItem mainBooking(LoadOrder loadOrder) {
        this.mainBooking = loadOrder;
        return this;
    }

    public void setMainBooking(LoadOrder loadOrder) {
        this.mainBooking = loadOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingItem)) {
            return false;
        }
        return id != null && id.equals(((BookingItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BookingItem{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", pickup='" + getPickup() + "'" +
            ", drop='" + getDrop() + "'" +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
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
