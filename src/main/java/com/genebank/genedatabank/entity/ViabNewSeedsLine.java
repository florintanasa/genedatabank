/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/

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
@Table(name = "VIAB_NEW_SEEDS_LINE", indexes = {
        @Index(name = "IDX_VIAB_NEW_SEEDS_LINE_VIAB_NEW_SEEDS", columnList = "VIAB_NEW_SEEDS_ID")
})
@Entity
public class ViabNewSeedsLine {
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

    @JoinColumn(name = "VIAB_NEW_SEEDS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ViabNewSeeds viabNewSeeds;

    @Column(name = "SEEDS_NUM")
    private Integer seedsNum;

    @Column(name = "GERM_START_DATE")
    private LocalDate germStartDate;

    @Column(name = "GERM_TIME")
    private Integer germTime;

    @Column(name = "GERM_EVAL_DATE")
    private LocalDate germEvalDate;

    @Column(name = "VIABLE_SEEDS")
    private Integer viableSeeds;

    @Column(name = "GERM_FACULTY")
    private Integer germFaculty;

    @Column(name = "COMMENTS")
    private String comments;

    public Integer getViableSeeds() {
        return viableSeeds;
    }

    public void setViableSeeds(Integer viableSeeds) {
        this.viableSeeds = viableSeeds;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String remarks) {
        this.comments = remarks;
    }

    public Integer getGermFaculty() {
        return germFaculty;
    }

    public void setGermFaculty(Integer germFaculty) {
        this.germFaculty = germFaculty;
    }

    public LocalDate getGermEvalDate() {
        return germEvalDate;
    }

    public void setGermEvalDate(LocalDate germevalDate) {
        this.germEvalDate = germevalDate;
    }

    public Integer getGermTime() {
        return germTime;
    }

    public void setGermTime(Integer germTime) {
        this.germTime = germTime;
    }

    public LocalDate getGermStartDate() {
        return germStartDate;
    }

    public void setGermStartDate(LocalDate germStartDate) {
        this.germStartDate = germStartDate;
    }

    public Integer getSeedsNum() {
        return seedsNum;
    }

    public void setSeedsNum(Integer seedsNum) {
        this.seedsNum = seedsNum;
    }

    public ViabNewSeeds getViabNewSeeds() {
        return viabNewSeeds;
    }

    public void setViabNewSeeds(ViabNewSeeds viabNewSeeds) {
        this.viabNewSeeds = viabNewSeeds;
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