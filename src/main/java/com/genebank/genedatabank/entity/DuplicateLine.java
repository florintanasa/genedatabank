package com.genebank.genedatabank.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "DUPLICATE_LINE", indexes = {
        @Index(name = "IDX_DUPLICATE_LINE_DUPLICATE", columnList = "DUPLICATE_ID"),
        @Index(name = "IDX_DUPLICATE_LINE_ID_DEPOSIT", columnList = "ID_DEPOSIT_ID")
})
@Entity
public class DuplicateLine {
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

    @JoinColumn(name = "DUPLICATE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Duplicate duplicate;

    @JoinColumn(name = "ID_DEPOSIT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Deposit id_deposit;

    @Column(name = "QUANTITY", nullable = false)
    @NotNull
    private Integer quantity;

    @Column(name = "D_DEPOSIT_CODE", nullable = false, length = 10)
    @NotNull
    private String dDepositCode;

    @Column(name = "D_STORAGE", nullable = false)
    @NotNull
    private String dStorage;

    @Column(name = "D_YEARSTORAGE", nullable = false, length = 8)
    @NotNull
    private String dYearstorage;

    @Column(name = "D_YEARMULTI")
    private Integer dYearmulti;

    @Column(name = "D_MULTIPLY")
    private Integer dMultiply;

    @Column(name = "D_YEARGERM")
    private Integer dYeargerm;

    @Column(name = "D_PERCENTAGE")
    private Integer dPercentage;

    @Column(name = "D_STOCK", nullable = false)
    @NotNull
    private Integer dStock;

    @Column(name = "D_HUMIDITY")
    private Double dHumidity;

    @Column(name = "D_MMB")
    private Double dMmb;

    @Column(name = "D_ORIGINAL", nullable = false)
    @NotNull
    private Boolean dOriginal = false;

    @Column(name = "P_ACCENUMB", nullable = false, length = 10)
    @NotNull
    private String pAccenumb;

    @Column(name = "P_DOI", length = 15)
    private String pDoi;

    @Column(name = "P_COLLNUMB", length = 10)
    private String pCollnumb;

    @Column(name = "P_COLLMISSID", length = 20)
    private String pCollmissid;

    @Column(name = "T_FAMMILY", length = 20)
    private String tFammily;

    @Column(name = "T_GENUS", length = 20)
    private String tGenus;

    @Column(name = "T_SPECIES", length = 90)
    private String tSpecies;

    @Column(name = "T_SPAUTHOR", length = 30)
    private String tSpauthor;

    @Column(name = "T_SUBTAXA", length = 55)
    private String tSubtaxa;

    @Column(name = "T_SUBAUTHOR", length = 30)
    private String tSubauthor;

    @Column(name = "T_CROPNUME", length = 110)
    private String tCropnume;

    @Column(name = "T_CROPNAME", length = 110)
    private String tCropname;

    public String getTCropname() {
        return tCropname;
    }

    public void setTCropname(String tCropname) {
        this.tCropname = tCropname;
    }

    public String getTCropnume() {
        return tCropnume;
    }

    public void setTCropnume(String tCropnume) {
        this.tCropnume = tCropnume;
    }

    public String getTSubauthor() {
        return tSubauthor;
    }

    public void setTSubauthor(String tSubauthor) {
        this.tSubauthor = tSubauthor;
    }

    public String getTSubtaxa() {
        return tSubtaxa;
    }

    public void setTSubtaxa(String tSubtaxa) {
        this.tSubtaxa = tSubtaxa;
    }

    public String getTSpauthor() {
        return tSpauthor;
    }

    public void setTSpauthor(String tSpauthor) {
        this.tSpauthor = tSpauthor;
    }

    public String getTSpecies() {
        return tSpecies;
    }

    public void setTSpecies(String tSpecies) {
        this.tSpecies = tSpecies;
    }

    public String getTGenus() {
        return tGenus;
    }

    public void setTGenus(String tGenus) {
        this.tGenus = tGenus;
    }

    public String getTFammily() {
        return tFammily;
    }

    public void setTFammily(String tFammily) {
        this.tFammily = tFammily;
    }

    public String getPCollmissid() {
        return pCollmissid;
    }

    public void setPCollmissid(String pCollmissid) {
        this.pCollmissid = pCollmissid;
    }

    public String getPCollnumb() {
        return pCollnumb;
    }

    public void setPCollnumb(String pCollnumb) {
        this.pCollnumb = pCollnumb;
    }

    public String getPDoi() {
        return pDoi;
    }

    public void setPDoi(String pDoi) {
        this.pDoi = pDoi;
    }

    public String getPAccenumb() {
        return pAccenumb;
    }

    public void setPAccenumb(String pAccenumb) {
        this.pAccenumb = pAccenumb;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getDOriginal() {
        return dOriginal;
    }

    public void setDOriginal(Boolean dOriginal) {
        this.dOriginal = dOriginal;
    }

    public Double getDMmb() {
        return dMmb;
    }

    public void setDMmb(Double dMmb) {
        this.dMmb = dMmb;
    }

    public Double getDHumidity() {
        return dHumidity;
    }

    public void setDHumidity(Double dHumidity) {
        this.dHumidity = dHumidity;
    }

    public Integer getDStock() {
        return dStock;
    }

    public void setDStock(Integer dStock) {
        this.dStock = dStock;
    }

    public Integer getDPercentage() {
        return dPercentage;
    }

    public void setDPercentage(Integer dPercentage) {
        this.dPercentage = dPercentage;
    }

    public Integer getDYeargerm() {
        return dYeargerm;
    }

    public void setDYeargerm(Integer dYeargerm) {
        this.dYeargerm = dYeargerm;
    }

    public Integer getDMultiply() {
        return dMultiply;
    }

    public void setDMultiply(Integer dMultiply) {
        this.dMultiply = dMultiply;
    }

    public Integer getDYearmulti() {
        return dYearmulti;
    }

    public void setDYearmulti(Integer dYearmulti) {
        this.dYearmulti = dYearmulti;
    }

    public String getDYearstorage() {
        return dYearstorage;
    }

    public void setDYearstorage(String dYearstorage) {
        this.dYearstorage = dYearstorage;
    }

    public String getDStorage() {
        return dStorage;
    }

    public void setDStorage(String d_storage) {
        this.dStorage = d_storage;
    }

    public String getDDepositCode() {
        return dDepositCode;
    }

    public void setDDepositCode(String dDepositCode) {
        this.dDepositCode = dDepositCode;
    }

    public Deposit getId_deposit() {
        return id_deposit;
    }

    public void setId_deposit(Deposit id_deposit) {
        this.id_deposit = id_deposit;
    }

    public Duplicate getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Duplicate duplicate) {
        this.duplicate = duplicate;
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