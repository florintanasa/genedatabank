/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/

package com.genebank.genedatabank.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "VIAB_NEW_SEEDS", indexes = {
        @Index(name = "IDX_VIAB_NEW_SEEDS_ID_ACCENUMB", columnList = "ID_ACCENUMB_ID"),
        @Index(name = "IDX_VIAB_NEW_SEEDS_UNQ", columnList = "ID_VNS", unique = true)
})
@Entity
public class ViabNewSeeds {
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

    @InstanceName
    @Column(name = "ID_VNS", nullable = false, length = 17)
    @NotNull
    private String idVNS;

    @JoinColumn(name = "ID_ACCENUMB_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Pasaport id_accenumb;

    @Column(name = "P_ACCNAME", nullable = false, length = 45)
    @NotNull
    private String pAccname;

    @Column(name = "P_GENUS", nullable = false)
    @NotNull
    private String pGenus;

    @Column(name = "P_SPECIES", nullable = false)
    @NotNull
    private String pSpecies;

    @Column(name = "STATUS", nullable = false)
    @NotNull
    private String status;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "viabNewSeeds")
    private List<ViabNewSeedsLine> viabnewseedsLines;

    @Column(name = "YEAR_TEST", nullable = false)
    @NotNull
    private Integer yearTest;

    @Column(name = "VIAB_PERCENT")
    private Integer viabPercent;

    public String getPSpecies() {
        return pSpecies;
    }

    public void setPSpecies(String pSpecies) {
        this.pSpecies = pSpecies;
    }

    public String getPGenus() {
        return pGenus;
    }

    public void setPGenus(String pGenus) {
        this.pGenus = pGenus;
    }

    public String getPAccname() {
        return pAccname;
    }

    public void setPAccname(String pAccname) {
        this.pAccname = pAccname;
    }

    public String getIdVNS() {
        return idVNS;
    }

    public void setIdVNS(String idVNS) {
        this.idVNS = idVNS;
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

    public List<ViabNewSeedsLine> getViabnewseedsLines() {
        return viabnewseedsLines;
    }

    public void setViabnewseedsLines(List<ViabNewSeedsLine> viabnewseedsLines) {
        this.viabnewseedsLines = viabnewseedsLines;
    }

    public ViabilityStatus getStatus() {
        return status == null ? null : ViabilityStatus.fromId(status);
    }

    public void setStatus(ViabilityStatus status) {
        this.status = status == null ? null : status.getId();
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