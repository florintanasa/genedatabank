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

import io.jmix.core.FileRef;
import io.jmix.core.MetadataTools;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author : Florin Tanasă
 * @since : 05.09.2023
 **/

@JmixEntity
@Table(name = "DEPOSIT", indexes = {
        @Index(name = "IDX_DEPOSIT_ID_STORAGE", columnList = "ID_STORAGE_ID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_DEPOSIT_UNQ_DEPOSIT_CODE", columnNames = {"DEPOSIT_CODE"})
})
@Entity
public class Deposit {
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

    @JoinColumn(name = "ID_ACCENUMB_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Pasaport id_accenumb;

    @Column(name = "DEPOSIT_CODE", nullable = false, length = 10)
    @NotNull
    private String deposit_code;

    @JoinColumn(name = "ID_STORAGE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Storage id_storage;

    @Column(name = "YEARSTORAGE", nullable = false, length = 8)
    @NotNull
    private String yearstorage;

    @Column(name = "YEARMULTI")
    private Integer yearmulti;

    @Column(name = "MULTIPLY")
    private Integer multiply;

    @Column(name = "YEARGERM")
    private Integer yeargerm;

    @Column(name = "PERCENTAGE")
    private Integer percentage;

    @Column(name = "STOCK", nullable = false)
    @NotNull
    private Integer stock;

    @Column(name = "HUMIDITY")
    private Double humidity;

    @Column(name = "MMB")
    private Double mmb;

    @Column(name = "COMMENTS")
    @Lob
    private String comments;

    @Column(name = "QRCODE", length = 1024)
    private FileRef qrcode;

    public void setYeargerm(Integer yeargerm) {
        this.yeargerm = yeargerm;
    }

    public Integer getYeargerm() {
        return yeargerm;
    }

    public void setYearmulti(Integer yearmulti) {
        this.yearmulti = yearmulti;
    }

    public Integer getYearmulti() {
        return yearmulti;
    }

    public FileRef getQrcode() {
        return qrcode;
    }

    public void setQrcode(FileRef qrcode) {
        this.qrcode = qrcode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getMmb() {
        return mmb;
    }

    public void setMmb(Double mmb) {
        this.mmb = mmb;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getMultiply() {
        return multiply;
    }

    public void setMultiply(Integer multiply) {
        this.multiply = multiply;
    }

    public String getYearstorage() {
        return yearstorage;
    }

    public void setYearstorage(String yearstorage) {
        this.yearstorage = yearstorage;
    }

    public Storage getId_storage() {
        return id_storage;
    }

    public void setId_storage(Storage id_storage) {
        this.id_storage = id_storage;
    }

    public String getDeposit_code() {
        return deposit_code;
    }

    public void setDeposit_code(String deposit_code) {
        this.deposit_code = deposit_code;
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

    @InstanceName
    @DependsOnProperties({"deposit_code", "stock"})
    public String getInstanceName(MetadataTools metadataTools, DatatypeFormatter datatypeFormatter) {
        return String.format("%s - %s",
                metadataTools.format(deposit_code),
                datatypeFormatter.formatInteger(stock));
    }
}