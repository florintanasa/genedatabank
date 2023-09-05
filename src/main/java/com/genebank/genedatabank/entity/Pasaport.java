/**
 * Copyright 2023 Florin Tanasă
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * *
 * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.genebank.genedatabank.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
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
import java.util.Set;
import java.util.UUID;

/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
 **/

@JmixEntity
@Table(name = "PASAPORT", indexes = {
        @Index(name = "IDX_PASAPORT_ID_TAXONOMY", columnList = "ID_TAXONOMY_ID"),
        @Index(name = "IDX_PASAPORT_ID_COUNTRY", columnList = "ID_COUNTRY_ID"),
        @Index(name = "IDX_PASAPORT_ID_COUNTYSIRUTA", columnList = "ID_COUNTYSIRUTA_ID"),
        @Index(name = "IDX_PASAPORT_ID_LOCALITYSIRUTA", columnList = "ID_LOCALITYSIRUTA_ID"),
        @Index(name = "IDX_PASAPORT_ID_GEOREFMETH", columnList = "ID_GEOREFMETH_ID"),
        @Index(name = "IDX_PASAPORT_ID_SAMPSTAT", columnList = "ID_SAMPSTAT_ID"),
        @Index(name = "IDX_PASAPORT_ID_COLLSRC", columnList = "ID_COLLSRC_ID"),
        @Index(name = "IDX_PASAPORT_ID_DONORCODE", columnList = "ID_DONORCODE_ID"),
        @Index(name = "IDX_PASAPORT_ID_ACCECONF", columnList = "ID_ACCECONF_ID"),
        @Index(name = "IDX_PASAPORT_ID_MLSSTAT", columnList = "ID_MLSSTAT_ID"),
        @Index(name = "IDX_PASAPORT_ID_AEGISSTAT", columnList = "ID_AEGISSTAT_ID"),
        @Index(name = "IDX_PASAPORT_ID_HISTORIC", columnList = "ID_HISTORIC_ID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_PASAPORT_UNQ_ACCENUMB", columnNames = {"ACCENUMB"})
})
@Entity
public class Pasaport {
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

    @JoinColumn(name = "ID_INSTCODE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Institute id_instcode;

    @InstanceName
    @Column(name = "ACCENUMB", nullable = false, length = 10)
    @NotNull
    private String accenumb;

    @Column(name = "DOI", length = 15)
    private String doi;

    @Column(name = "COLLNUMB", length = 10)
    private String collnumb;

    @JoinTable(name = "PASAPORT_COLLETING_INSTITUTE_LINK",
            joinColumns = @JoinColumn(name = "PASAPORT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private Set<Institute> id_collcode;

    @Column(name = "COLLMISSID", length = 20)
    private String collmissid;

    @JoinColumn(name = "ID_TAXONOMY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Taxonomy id_taxonomy;

    @Column(name = "ACCNAME", length = 45)
    private String accname;

    @Column(name = "ACQDATE", length = 8)
    private String acqdate;

    @Column(name = "ORIGDATE", length = 8)
    private String origdate;

    @JoinColumn(name = "ID_COUNTRY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Country id_country;

    @JoinColumn(name = "ID_COUNTYSIRUTA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Countysiruta id_countysiruta;

    @JoinColumn(name = "ID_LOCALITYSIRUTA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Localitysiruta id_localitysiruta;

    @PropertyDatatype("geocoordinate")
    @Column(name = "LATITUDE")
    private Double latitude;

    @PropertyDatatype("geocoordinate")
    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "ELEVATION")
    private Integer elevation;

    @JoinColumn(name = "ID_GEOREFMETH_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Georefmeth id_georefmeth;

    @Column(name = "COLLDATE", length = 8)
    private String colldate;

    @JoinTable(name = "PASAPORT_BREEDING_INSTITUTE_LINK",
            joinColumns = @JoinColumn(name = "PASAPORT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private Set<Institute> id_bredcode;

    @JoinColumn(name = "ID_SAMPSTAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sampstat id_sampstat;

    @Column(name = "ANCEST", length = 60)
    private String ancest;

    @JoinColumn(name = "ID_COLLSRC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Collsrc id_collsrc;

    @JoinColumn(name = "ID_DONORCODE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Institute id_donorcode;

    @Column(name = "DONORNUMB", length = 20)
    private String donornumb;

    @Column(name = "OTHERNUMB", length = 20)
    private String othernumb;

    @Column(name = "TEMPNUMB", length = 9)
    private String tempnumb;

    @JoinTable(name = "PASAPORT_DUPLICATES_INSTITUTE_LINK",
            joinColumns = @JoinColumn(name = "PASAPORT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private Set<Institute> id_duplsite;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "COMMENTS")
    @Lob
    private String comments;

    @Column(name = "ACCEURL")
    private String acceurl;

    @JoinColumn(name = "ID_ACCECONF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Acceconf id_acceconf;

    @JoinColumn(name = "ID_MLSSTAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mlsstat id_mlsstat;

    @JoinColumn(name = "ID_AEGISSTAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Aegisstat id_aegisstat;

    @JoinColumn(name = "ID_HISTORIC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Historic id_historic;

    public Set<Institute> getId_collcode() {
        return id_collcode;
    }

    public void setId_collcode(Set<Institute> id_collcode) {
        this.id_collcode = id_collcode;
    }

    public Set<Institute> getId_duplsite() {
        return id_duplsite;
    }

    public void setId_duplsite(Set<Institute> id_duplsite) {
        this.id_duplsite = id_duplsite;
    }

    public Set<Institute> getId_bredcode() {
        return id_bredcode;
    }

    public void setId_bredcode(Set<Institute> id_bredcode) {
        this.id_bredcode = id_bredcode;
    }

    public Historic getId_historic() {
        return id_historic;
    }

    public void setId_historic(Historic id_historic) {
        this.id_historic = id_historic;
    }

    public Aegisstat getId_aegisstat() {
        return id_aegisstat;
    }

    public void setId_aegisstat(Aegisstat id_aegisstat) {
        this.id_aegisstat = id_aegisstat;
    }

    public Mlsstat getId_mlsstat() {
        return id_mlsstat;
    }

    public void setId_mlsstat(Mlsstat id_mlsstat) {
        this.id_mlsstat = id_mlsstat;
    }

    public Acceconf getId_acceconf() {
        return id_acceconf;
    }

    public void setId_acceconf(Acceconf id_acceconf) {
        this.id_acceconf = id_acceconf;
    }

    public String getAcceurl() {
        return acceurl;
    }

    public void setAcceurl(String acceurl) {
        this.acceurl = acceurl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTempnumb() {
        return tempnumb;
    }

    public void setTempnumb(String tempnumb) {
        this.tempnumb = tempnumb;
    }

    public String getOthernumb() {
        return othernumb;
    }

    public void setOthernumb(String othernumb) {
        this.othernumb = othernumb;
    }

    public String getDonornumb() {
        return donornumb;
    }

    public void setDonornumb(String donornumb) {
        this.donornumb = donornumb;
    }

    public Institute getId_donorcode() {
        return id_donorcode;
    }

    public void setId_donorcode(Institute id_donorcode) {
        this.id_donorcode = id_donorcode;
    }

    public Collsrc getId_collsrc() {
        return id_collsrc;
    }

    public void setId_collsrc(Collsrc id_collsrc) {
        this.id_collsrc = id_collsrc;
    }

    public String getAncest() {
        return ancest;
    }

    public void setAncest(String ancest) {
        this.ancest = ancest;
    }

    public Sampstat getId_sampstat() {
        return id_sampstat;
    }

    public void setId_sampstat(Sampstat id_sampstat) {
        this.id_sampstat = id_sampstat;
    }

    public String getColldate() {
        return colldate;
    }

    public void setColldate(String colldate) {
        this.colldate = colldate;
    }

    public Georefmeth getId_georefmeth() {
        return id_georefmeth;
    }

    public void setId_georefmeth(Georefmeth id_georefmeth) {
        this.id_georefmeth = id_georefmeth;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Localitysiruta getId_localitysiruta() {
        return id_localitysiruta;
    }

    public void setId_localitysiruta(Localitysiruta id_localitysiruta) {
        this.id_localitysiruta = id_localitysiruta;
    }

    public Countysiruta getId_countysiruta() {
        return id_countysiruta;
    }

    public void setId_countysiruta(Countysiruta id_countysiruta) {
        this.id_countysiruta = id_countysiruta;
    }

    public Country getId_country() {
        return id_country;
    }

    public void setId_country(Country id_country) {
        this.id_country = id_country;
    }

    public String getOrigdate() {
        return origdate;
    }

    public void setOrigdate(String origdate) {
        this.origdate = origdate;
    }

    public String getAcqdate() {
        return acqdate;
    }

    public void setAcqdate(String acqdate) {
        this.acqdate = acqdate;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public Taxonomy getId_taxonomy() {
        return id_taxonomy;
    }

    public void setId_taxonomy(Taxonomy id_taxonomy) {
        this.id_taxonomy = id_taxonomy;
    }

    public String getCollmissid() {
        return collmissid;
    }

    public void setCollmissid(String collmissid) {
        this.collmissid = collmissid;
    }

    public String getCollnumb() {
        return collnumb;
    }

    public void setCollnumb(String collnumb) {
        this.collnumb = collnumb;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getAccenumb() {
        return accenumb;
    }

    public void setAccenumb(String accenumb) {
        this.accenumb = accenumb;
    }

    public Institute getId_instcode() {
        return id_instcode;
    }

    public void setId_instcode(Institute id_instcode) {
        this.id_instcode = id_instcode;
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