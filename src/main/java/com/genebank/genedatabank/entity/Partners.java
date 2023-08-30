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

import io.jmix.core.MetadataTools;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author : Florin Tanasă
 * @since : 30.08.2023
 **/

@JmixEntity
@Table(name = "PARTNERS", indexes = {
        @Index(name = "IDX_PARTNERS_ID_COUNTRY", columnList = "ID_COUNTRY_ID"),
        @Index(name = "IDX_PARTNERS_ID_LOCALITY", columnList = "ID_LOCALITY_ID"),
        @Index(name = "IDX_PARTNERS_ID_ROADTYPE", columnList = "ID_ROADTYPE_ID"),
        @Index(name = "IDX_PARTNERS_ID_STREET", columnList = "ID_STREET_ID")
})
@Entity
public class Partners {
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

    @Column(name = "FIRST_NAME", length = 50)
    private String first_name;

    @Column(name = "FAMILY_NAME", length = 50)
    private String family_name;

    @Column(name = "COMPANY_NAME", length = 150)
    private String company_name;

    @JoinColumn(name = "ID_COUNTRY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Country id_country;

    @JoinColumn(name = "ID_LOCALITY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Localitysiruta id_locality;

    @JoinColumn(name = "ID_ROADTYPE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Roadtype id_roadtype;

    @JoinColumn(name = "ID_STREET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Street id_street;

    @Column(name = "STREET_NB", length = 15)
    private String street_nb;

    @Column(name = "BLOCK", length = 15)
    private String block;

    @Column(name = "STAIR", length = 15)
    private String stair;

    @Column(name = "FLOOR_", length = 15)
    private String floor;

    @Column(name = "APARTMENT", length = 15)
    private String apartment;

    @Column(name = "POSTAL_CODE", length = 15)
    private String postal_code;

    @Email(message = "{msg://com.genebank.genedatabank.entity/Partners.email.validation.Email}", regexp = "\\S+@\\S+")
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE", length = 12)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getStair() {
        return stair;
    }

    public void setStair(String stair) {
        this.stair = stair;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStreet_nb() {
        return street_nb;
    }

    public void setStreet_nb(String street_nb) {
        this.street_nb = street_nb;
    }

    public Street getId_street() {
        return id_street;
    }

    public void setId_street(Street id_street) {
        this.id_street = id_street;
    }

    public Roadtype getId_roadtype() {
        return id_roadtype;
    }

    public void setId_roadtype(Roadtype id_roadtype) {
        this.id_roadtype = id_roadtype;
    }

    public Localitysiruta getId_locality() {
        return id_locality;
    }

    public void setId_locality(Localitysiruta id_locality) {
        this.id_locality = id_locality;
    }

    public Country getId_country() {
        return id_country;
    }

    public void setId_country(Country id_country) {
        this.id_country = id_country;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
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
    @DependsOnProperties({"first_name", "family_name"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s %s",
                metadataTools.format(first_name),
                metadataTools.format(family_name));
    }
}