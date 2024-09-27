package com.genebank.genedatabank.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author : Florin Tanasă
 * @since : 27.09.2024
 **/

@JmixEntity
@Table(name = "DUPLICATE", indexes = {
        @Index(name = "IDX_DUPLICATE_DUPLICATE_INSTITUTE", columnList = "ID_DUPLICATE_INSTITUTE_ID"),
        @Index(name = "IDX_DUPLICATE_ID_DEPOSIT_CODE", columnList = "ID_DEPOSIT_CODE_ID")
})
@Entity
public class Duplicate {
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

    @JoinColumn(name = "ID_DUPLICATE_INSTITUTE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Institute id_duplicate_institute;

    @PositiveOrZero(message = "{msg://com.genebank.genedatabank.entity/Duplicate.depositBoxNumb.validation.PositiveOrZero}")
    @NotNull
    @Column(name = "DEPOSIT_BOX_NUMB", nullable = false)
    private Integer depositBoxNumb;

    @JoinColumn(name = "ID_DEPOSIT_CODE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Deposit id_deposit_code;

    @Column(name = "IS_TEST", nullable = false)
    @NotNull
    private Boolean isTest = false;

    @Column(name = "IN_CURENT_BOX", nullable = false)
    @NotNull
    private Boolean inCurentBox = false;

    public void setDepositBoxNumb(Integer depositBoxNumb) {
        this.depositBoxNumb = depositBoxNumb;
    }

    public Integer getDepositBoxNumb() {
        return depositBoxNumb;
    }

    public Boolean getInCurentBox() {
        return inCurentBox;
    }

    public void setInCurentBox(Boolean inCurentBox) {
        this.inCurentBox = inCurentBox;
    }

    public Boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }

    public Deposit getId_deposit_code() {
        return id_deposit_code;
    }

    public void setId_deposit_code(Deposit id_deposit_code) {
        this.id_deposit_code = id_deposit_code;
    }

    public Institute getId_duplicate_institute() {
        return id_duplicate_institute;
    }

    public void setId_duplicate_institute(Institute duplicate_institute) {
        this.id_duplicate_institute = duplicate_institute;
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