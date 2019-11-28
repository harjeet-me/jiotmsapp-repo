package com.jio.tmsapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.jio.tmsapp.domain.enumeration.InvoiceStatus;

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

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "invoice_tax_total")
    private Double invoiceTaxTotal;

    @Column(name = "invoice_sub_total")
    private Double invoiceSubTotal;

    @Column(name = "invoice_total")
    private Double invoiceTotal;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_due_date")
    private LocalDate invoiceDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

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

    public String getOrderNo() {
        return orderNo;
    }

    public Invoice orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getInvoiceTaxTotal() {
        return invoiceTaxTotal;
    }

    public Invoice invoiceTaxTotal(Double invoiceTaxTotal) {
        this.invoiceTaxTotal = invoiceTaxTotal;
        return this;
    }

    public void setInvoiceTaxTotal(Double invoiceTaxTotal) {
        this.invoiceTaxTotal = invoiceTaxTotal;
    }

    public Double getInvoiceSubTotal() {
        return invoiceSubTotal;
    }

    public Invoice invoiceSubTotal(Double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
        return this;
    }

    public void setInvoiceSubTotal(Double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
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

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public Invoice invoiceDueDate(LocalDate invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        return this;
    }

    public void setInvoiceDueDate(LocalDate invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Invoice status(InvoiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatus status) {
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
            ", orderNo='" + getOrderNo() + "'" +
            ", invoiceTaxTotal=" + getInvoiceTaxTotal() +
            ", invoiceSubTotal=" + getInvoiceSubTotal() +
            ", invoiceTotal=" + getInvoiceTotal() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoiceDueDate='" + getInvoiceDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
