package com.genebank.genedatabank.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "OUTSEEDS", indexes = {
        @Index(name = "IDX_OUTSEEDS_ID_ACCENUMB", columnList = "ID_ACCENUMB_ID"),
        @Index(name = "IDX_OUTSEEDS_ID_DEPOSIT_CODE", columnList = "ID_DEPOSIT_CODE_ID"),
        @Index(name = "IDX_OUTSEEDS_ID_PARTNER_NAME", columnList = "ID_PARTNER_NAME_ID"),
        @Index(name = "IDX_OUTSEEDS_ID_SCOPE_CODESPEC", columnList = "ID_SCOPE_CODESPEC_ID"),
        @Index(name = "IDX_OUTSEEDS_ID_CURATOR_NAME", columnList = "ID_CURATOR_NAME_ID")
})
@Entity
public class Outseeds {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private OffsetDateTime lastModifiedDate;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    private OffsetDateTime deletedDate;

    @JoinColumn(name = "ID_ACCENUMB_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pasaport id_accenumb;

    @Column(name = "ACCENUMB_OLD", length = 11)
    private String accenumb_old;

    @JoinColumn(name = "ID_DEPOSIT_CODE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Deposit id_deposit_code;

    @Column(name = "DEPOSIT_OLD", length = 8)
    private String deposit_old;

    @Column(name = "OUT_DATE")
    private LocalDate out_date;

    @Column(name = "DATE_OLD", length = 8)
    private String date_old;

    @Column(name = "OUT_STOCK")
    private Integer out_stock;

    @JoinColumn(name = "ID_PARTNER_NAME_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Partners id_partner_name;

    @JoinColumn(name = "ID_SCOPE_CODESPEC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Scope id_scope_codespec;

    @JoinColumn(name = "ID_CURATOR_NAME_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curators id_curator_name;

    @Column(name = "RESPONSIBLE", length = 35)
    private String responsible;

    @Column(name = "OPERATOR", length = 35)
    private String operator;

    @Column(name = "NOTE")
    private String note;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate_old() {
        return date_old;
    }

    public void setDate_old(String date_old) {
        this.date_old = date_old;
    }

    public String getDeposit_old() {
        return deposit_old;
    }

    public void setDeposit_old(String deposit_old) {
        this.deposit_old = deposit_old;
    }

    public String getAccenumb_old() {
        return accenumb_old;
    }

    public void setAccenumb_old(String accenumb_old) {
        this.accenumb_old = accenumb_old;
    }

    public Curators getId_curator_name() {
        return id_curator_name;
    }

    public void setId_curator_name(Curators id_curator_name) {
        this.id_curator_name = id_curator_name;
    }

    public Scope getId_scope_codespec() {
        return id_scope_codespec;
    }

    public void setId_scope_codespec(Scope id_scope_codespec) {
        this.id_scope_codespec = id_scope_codespec;
    }

    public Partners getId_partner_name() {
        return id_partner_name;
    }

    public void setId_partner_name(Partners id_partner_name) {
        this.id_partner_name = id_partner_name;
    }

    public Integer getOut_stock() {
        return out_stock;
    }

    public void setOut_stock(Integer out_stock) {
        this.out_stock = out_stock;
    }

    public LocalDate getOut_date() {
        return out_date;
    }

    public void setOut_date(LocalDate out_date) {
        this.out_date = out_date;
    }

    public Deposit getId_deposit_code() {
        return id_deposit_code;
    }

    public void setId_deposit_code(Deposit id_deposit_code) {
        this.id_deposit_code = id_deposit_code;
    }

    public Pasaport getId_accenumb() {
        return id_accenumb;
    }

    public void setId_accenumb(Pasaport id_accenumb) {
        this.id_accenumb = id_accenumb;
    }

    public OffsetDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(OffsetDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}