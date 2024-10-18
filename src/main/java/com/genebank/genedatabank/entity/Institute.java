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
 * @since : 30.08.2023
 **/

@JmixEntity
@Table(name = "INSTITUTE", indexes = {
        @Index(name = "IDX_INSTITUTE_ID_COUNTY", columnList = "ID_COUNTY_ID"),
        @Index(name = "IDX_INSTITUTE_ID_LOCALITY", columnList = "ID_LOCALITY_ID"),
        @Index(name = "IDX_INSTITUTE_ID_ROAD_TYPE", columnList = "ID_ROAD_TYPE_ID"),
        @Index(name = "IDX_INSTITUTE_ID_STREET", columnList = "ID_STREET_ID")
})
@Entity
public class Institute {
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

    @Column(name = "INSTCODE", length = 7)
    private String instcode;

    @Column(name = "ACRONYM", length = 24)
    private String acronym;

    @InstanceName
    @Column(name = "FULL_NAME_RO", nullable = false, length = 200)
    @NotNull
    private String full_name_ro;

    @Column(name = "FULL_NAME_EN", nullable = false, length = 200)
    @NotNull
    private String full_name_en;

    @JoinColumn(name = "ID_COUNTRY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Country id_country;

    @JoinColumn(name = "ID_COUNTY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Countysiruta id_county;

    @JoinColumn(name = "ID_LOCALITY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Localitysiruta id_locality;

    @JoinColumn(name = "ID_ROAD_TYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Roadtype id_roadType;

    @JoinColumn(name = "ID_STREET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Street id_street;

    @Column(name = "STREET_NUM", length = 15)
    private String streetNum;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POST_CODE", length = 15)
    private String postCode;

    @Column(name = "URL", length = 200)
    private String url;

    @Column(name = "SERIAL_VNS", length = 25)
    private String serialVNS;

    @Column(name = "SERIAL_VOS", length = 25)
    private String serialVOS;

    @Column(name = "SERIAL_ACCENUMB", length = 25)
    private String serialAccenumb;

    @Column(name = "SERIAL_ACCENUMB_TEMP", length = 25)
    private String serialAccenumbTemp;

    @Column(name = "API_KEY_GOOGLE_MAPS")
    private String apiKeyGoogleMaps;

    @JoinTable(name = "PASAPORT_BREEDING_INSTITUTE_LINK",
            joinColumns = @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PASAPORT_ID", referencedColumnName = "ID"))
    @ManyToMany
    private Set<Pasaport> pasaports_bredcode;

    @JoinTable(name = "PASAPORT_DUPLICATES_INSTITUTE_LINK",
            joinColumns = @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PASAPORT_ID", referencedColumnName = "ID"))
    @ManyToMany
    private Set<Pasaport> pasaports_duplsite;

    @JoinTable(name = "PASAPORT_COLLETING_INSTITUTE_LINK",
            joinColumns = @JoinColumn(name = "INSTITUTE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PASAPORT_ID", referencedColumnName = "ID"))
    @ManyToMany
    private Set<Pasaport> pasaports_collcode;

    public String getApiKeyGoogleMaps() {
        return apiKeyGoogleMaps;
    }

    public void setApiKeyGoogleMaps(String apiKeyGoogleMaps) {
        this.apiKeyGoogleMaps = apiKeyGoogleMaps;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public void setId_county(Countysiruta id_county) {
        this.id_county = id_county;
    }

    public Countysiruta getId_county() {
        return id_county;
    }

    public Street getId_street() {
        return id_street;
    }

    public void setId_street(Street id_street) {
        this.id_street = id_street;
    }

    public Roadtype getId_roadType() {
        return id_roadType;
    }

    public void setId_roadType(Roadtype id_roadType) {
        this.id_roadType = id_roadType;
    }

    public String getSerialAccenumbTemp() {
        return serialAccenumbTemp;
    }

    public void setSerialAccenumbTemp(String serialAccenumbTemp) {
        this.serialAccenumbTemp = serialAccenumbTemp;
    }

    public String getSerialAccenumb() {
        return serialAccenumb;
    }

    public void setSerialAccenumb(String serialAccenumb) {
        this.serialAccenumb = serialAccenumb;
    }

    public String getSerialVOS() {
        return serialVOS;
    }

    public void setSerialVOS(String serialVOS) {
        this.serialVOS = serialVOS;
    }

    public String getSerialVNS() {
        return serialVNS;
    }

    public void setSerialVNS(String serialVNS) {
        this.serialVNS = serialVNS;
    }

    public Localitysiruta getId_locality() {
        return id_locality;
    }

    public void setId_locality(Localitysiruta id_locality) {
        this.id_locality = id_locality;
    }

    public void setId_country(Country alpha3) {
        this.id_country = alpha3;
    }

    public Country getId_country() {
        return id_country;
    }

    public Set<Pasaport> getPasaports_collcode() {
        return pasaports_collcode;
    }

    public void setPasaports_collcode(Set<Pasaport> pasaports_collcode) {
        this.pasaports_collcode = pasaports_collcode;
    }

    public Set<Pasaport> getPasaports_duplsite() {
        return pasaports_duplsite;
    }

    public void setPasaports_duplsite(Set<Pasaport> pasaports_duplsite) {
        this.pasaports_duplsite = pasaports_duplsite;
    }

    public Set<Pasaport> getPasaports_bredcode() {
        return pasaports_bredcode;
    }

    public void setPasaports_bredcode(Set<Pasaport> pasaports_bredcode) {
        this.pasaports_bredcode = pasaports_bredcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFull_name_en() {
        return full_name_en;
    }

    public void setFull_name_en(String full_name_en) {
        this.full_name_en = full_name_en;
    }

    public String getFull_name_ro() {
        return full_name_ro;
    }

    public void setFull_name_ro(String full_name_ro) {
        this.full_name_ro = full_name_ro;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
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