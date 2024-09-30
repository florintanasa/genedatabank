package com.genebank.genedatabank.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author : Florin Tanasă
 * @since : 27.09.2024
 **/

@JmixEntity
@Table(name = "DUPLICATE", indexes = {
        @Index(name = "IDX_DUPLICATE_DUPLICATE_INSTITUTE", columnList = "ID_DUPLICATE_INSTITUTE_ID")
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

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    private OffsetDateTime deletedDate;

    @JoinColumn(name = "ID_DUPLICATE_INSTITUTE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Institute id_duplicate_institute;

    @PositiveOrZero(message = "{msg://com.genebank.genedatabank.entity/Duplicate.depositBoxNumb.validation.PositiveOrZero}")
    @NotNull
    @Column(name = "DEPOSIT_BOX_NUMB", nullable = false)
    private Integer depositBoxNumb;

    @Column(name = "THE_DATE", nullable = false)
    @NotNull
    private LocalDate theDate;

    @Column(name = "SEND_DATE")
    private LocalDate sendDate;

    @Column(name = "STATUS", nullable = false)
    @NotNull
    private String status;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "duplicate")
    private List<DuplicateLine> duplicateLines;

    public List<DuplicateLine> getDuplicateLines() {
        return duplicateLines;
    }

    public void setDuplicateLines(List<DuplicateLine> duplicateLines) {
        this.duplicateLines = duplicateLines;
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

    public DuplicateStatus getStatus() {
        return status == null ? null : DuplicateStatus.fromId(status);
    }

    public void setStatus(DuplicateStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public LocalDate getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    public LocalDate getTheDate() {
        return theDate;
    }

    public void setTheDate(LocalDate theDate) {
        this.theDate = theDate;
    }

    public void setDepositBoxNumb(Integer depositBoxNumb) {
        this.depositBoxNumb = depositBoxNumb;
    }

    public Integer getDepositBoxNumb() {
        return depositBoxNumb;
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