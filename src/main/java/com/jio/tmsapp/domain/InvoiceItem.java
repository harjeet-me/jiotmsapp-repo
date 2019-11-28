package com.jio.tmsapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.jio.tmsapp.domain.enumeration.InvoiceStatus;

/**
 * A InvoiceItem.
 */
@Entity
@Table(name = "invoice_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invoiceitem")
public class InvoiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "price")
    private Integer price;

    @Column(name = "total")
    private Integer total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "bol")
    private String bol;

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

    public InvoiceItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQty() {
        return qty;
    }

    public InvoiceItem qty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPrice() {
        return price;
    }

    public InvoiceItem price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotal() {
        return total;
    }

    public InvoiceItem total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public InvoiceItem status(InvoiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public InvoiceItem shipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
        return this;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getBol() {
        return bol;
    }

    public InvoiceItem bol(String bol) {
        this.bol = bol;
        return this;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceItem)) {
            return false;
        }
        return id != null && id.equals(((InvoiceItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", qty=" + getQty() +
            ", price=" + getPrice() +
            ", total=" + getTotal() +
            ", status='" + getStatus() + "'" +
            ", shipmentNumber='" + getShipmentNumber() + "'" +
            ", bol='" + getBol() + "'" +
            "}";
    }
}
