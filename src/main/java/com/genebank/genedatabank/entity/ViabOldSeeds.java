/**
 * @author : Florin Tanasă
 * @since : 21.10.2024
 **/

package com.genebank.genedatabank.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "VIAB_OLD_SEEDS", indexes = {
        @Index(name = "IDX_VIAB_OLD_SEEDS_ID_DEPOSIT_CODE", columnList = "ID_DEPOSIT_CODE_ID")
})
@Entity
public class ViabOldSeeds {
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

    @Column(name = "ID_VOS", length = 15)
    private String idVOS;

    @JoinColumn(name = "ID_DEPOSIT_CODE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Deposit id_deposit_code;

    @Column(name = "STOCK")
    private Integer stock;

    @Column(name = "LAST_YEAR_TEST")
    private Integer lastYearTest;

    @Column(name = "LAST_VIAB_TEST")
    private Integer lastViabTest;

    @Column(name = "YEAR_TEST")
    private Integer yearTest;

    @Column(name = "VIAB_PERCENT")
    private Integer viabPercent;

    @Column(name = "STATUS")
    private String status;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "viabOldSeeds")
    private List<ViabOldSeedsLine> viaboldseedsLines;

    public List<ViabOldSeedsLine> getViaboldseedsLines() {
        return viaboldseedsLines;
    }

    public void setViaboldseedsLines(List<ViabOldSeedsLine> viaboldseedsLines) {
        this.viaboldseedsLines = viaboldseedsLines;
    }

    public ViabilityStatus getStatus() {
        return status == null ? null : ViabilityStatus.fromId(status);
    }

    public void setStatus(ViabilityStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getViabPercent() {
        return viabPercent;
    }

    public void setViabPercent(Integer viabPercent) {
        this.viabPercent = viabPercent;
    }

    public Integer getYearTest() {
        return yearTest;
    }

    public void setYearTest(Integer yearTest) {
        this.yearTest = yearTest;
    }

    public Integer getLastViabTest() {
        return lastViabTest;
    }

    public void setLastViabTest(Integer lastViabTest) {
        this.lastViabTest = lastViabTest;
    }

    public Integer getLastYearTest() {
        return lastYearTest;
    }

    public void setLastYearTest(Integer lastYearTest) {
        this.lastYearTest = lastYearTest;
    }

    public Deposit getId_deposit_code() {
        return id_deposit_code;
    }

    public void setId_deposit_code(Deposit id_deposit_code) {
        this.id_deposit_code = id_deposit_code;
    }

    public String getIdVOS() {
        return idVOS;
    }

    public void setIdVOS(String idVOS) {
        this.idVOS = idVOS;
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