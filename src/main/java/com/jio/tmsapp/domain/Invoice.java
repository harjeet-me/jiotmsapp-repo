package com.jio.tmsapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.jio.tmsapp.domain.enumeration.StatusEnum;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "booking_no")
    private String bookingNo;

    @Column(name = "invoice_total")
    private Double invoiceTotal;

    @Column(name = "invoice_due_date")
    private Instant invoiceDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Customer invoiceTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public Invoice bookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public Invoice invoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
        return this;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Instant getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public Invoice invoiceDueDate(Instant invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        return this;
    }

    public void setInvoiceDueDate(Instant invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Invoice status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Customer getInvoiceTo() {
        return invoiceTo;
    }

    public Invoice invoiceTo(Customer customer) {
        this.invoiceTo = customer;
        return this;
    }

    public void setInvoiceTo(Customer customer) {
        this.invoiceTo = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", bookingNo='" + getBookingNo() + "'" +
            ", invoiceTotal=" + getInvoiceTotal() +
            ", invoiceDueDate='" + getInvoiceDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
