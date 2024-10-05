package com.genebank.genedatabank.entity;

import io.jmix.core.MetadataTools;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
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

    @Column(name = "P_ACCENAME", length = 45)
    private String pAccename;

    @Column(name = "P_ACQDATE", length = 8)
    private String pAcqdate;

    @Column(name = "P_ORIGDATE", length = 8)
    private String pOrigdate;

    @Column(name = "STATE_NAME", length = 35)
    private String stateName;

    @Column(name = "COUNTY_NAME", length = 25)
    private String countyName;

    @Column(name = "LOCALITY_NAME", length = 35)
    private String localityName;

    @PropertyDatatype("geocoordinate")
    @Column(name = "P_LATITUDE")
    private Double pLatitude;

    @PropertyDatatype("geocoordinate")
    @Column(name = "P_LONGITUDE")
    private Double pLongitude;

    @Column(name = "P_ELEVATION")
    private Integer pElevation;

    @Column(name = "G_NAME", length = 50)
    private String gName;

    @Column(name = "P_COLLDATE", length = 8)
    private String pColldate;

    @Column(name = "S_NAME")
    private String sName;

    @Column(name = "P_ANCEST")
    private String pAncest;

    @Column(name = "C_NAME", length = 50)
    private String cName;

    @Column(name = "DONOR_INSTCODE", length = 7)
    private String donorInstcode;

    @Column(name = "P_DONORNUMB", length = 20)
    private String pDonornumb;

    @Column(name = "P_OTHERNUMB", length = 20)
    private String pOthernumb;

    @Column(name = "P_TEMPNUMB", length = 9)
    private String pTempnumb;

    @Column(name = "P_ACCEURL")
    private String pAcceurl;

    @Column(name = "P_ACCECONF", length = 50)
    private String pAcceconf;

    @Column(name = "M_NAME", length = 50)
    private String mName;

    @Column(name = "A_NAME", length = 50)
    private String aName;

    @Column(name = "H_NAME", length = 50)
    private String hName;

    @Column(name = "T_CULT_CATEG")
    private String tCultCateg;

    public String getTCultCateg() {
        return tCultCateg;
    }

    public void setTCultCateg(String tCultCateg) {
        this.tCultCateg = tCultCateg;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public Integer getPElevation() {
        return pElevation;
    }

    public void setPElevation(Integer pElevation) {
        this.pElevation = pElevation;
    }

    public Double getPLongitude() {
        return pLongitude;
    }

    public void setPLongitude(Double pLongitude) {
        this.pLongitude = pLongitude;
    }

    public Double getPLatitude() {
        return pLatitude;
    }

    public void setPLatitude(Double pLatitude) {
        this.pLatitude = pLatitude;
    }

    public String getHName() {
        return hName;
    }

    public void setHName(String hName) {
        this.hName = hName;
    }

    public String getAName() {
        return aName;
    }

    public void setAName(String aName) {
        this.aName = aName;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getPAcceconf() {
        return pAcceconf;
    }

    public void setPAcceconf(String pAcceconf) {
        this.pAcceconf = pAcceconf;
    }

    public String getPAcceurl() {
        return pAcceurl;
    }

    public void setPAcceurl(String pAcceurl) {
        this.pAcceurl = pAcceurl;
    }

    public String getPTempnumb() {
        return pTempnumb;
    }

    public void setPTempnumb(String pTempnumb) {
        this.pTempnumb = pTempnumb;
    }

    public String getPOthernumb() {
        return pOthernumb;
    }

    public void setPOthernumb(String pOthernumb) {
        this.pOthernumb = pOthernumb;
    }

    public String getPDonornumb() {
        return pDonornumb;
    }

    public void setPDonornumb(String pDonornumb) {
        this.pDonornumb = pDonornumb;
    }

    public String getDonorInstcode() {
        return donorInstcode;
    }

    public void setDonorInstcode(String donorInstcode) {
        this.donorInstcode = donorInstcode;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getPAncest() {
        return pAncest;
    }

    public void setPAncest(String pAncest) {
        this.pAncest = pAncest;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getPColldate() {
        return pColldate;
    }

    public void setPColldate(String pColldate) {
        this.pColldate = pColldate;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPOrigdate() {
        return pOrigdate;
    }

    public void setPOrigdate(String pOrigdate) {
        this.pOrigdate = pOrigdate;
    }

    public String getPAcqdate() {
        return pAcqdate;
    }

    public void setPAcqdate(String pAcqdate) {
        this.pAcqdate = pAcqdate;
    }

    public String getPAccename() {
        return pAccename;
    }

    public void setPAccename(String pAccename) {
        this.pAccename = pAccename;
    }

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

    @InstanceName
    @DependsOnProperties({"duplicate", "dDepositCode", "pAccenumb"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s %s %s",
                metadataTools.format(duplicate),
                metadataTools.format(dDepositCode),
                metadataTools.format(pAccenumb));
    }
}