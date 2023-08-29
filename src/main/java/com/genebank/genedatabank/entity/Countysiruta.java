/**
 * @author : Florin Tanasă
 * @since : 29.08.2023
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
import java.util.UUID;

@JmixEntity
@Table(name = "COUNTYSIRUTA", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_COUNTYSIRUTA_UNQ_SIRUTA", columnNames = {"SIRUTA"}),
        @UniqueConstraint(name = "IDX_COUNTYSIRUTA_UNQ_CODE", columnNames = {"CODE"}),
        @UniqueConstraint(name = "IDX_COUNTYSIRUTA_UNQ_NAME", columnNames = {"NAME"}),
        @UniqueConstraint(name = "IDX_COUNTYSIRUTA_UNQ_MNEMONIC", columnNames = {"MNEMONIC"})
})
@Entity
public class Countysiruta {
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

    @Column(name = "SIRUTA", nullable = false)
    @NotNull
    private Integer siruta;

    @Column(name = "CODE", nullable = false)
    @NotNull
    private Integer code;

    @InstanceName
    @Column(name = "NAME", nullable = false, length = 25)
    @NotNull
    private String name;

    @Column(name = "MNEMONIC", nullable = false, length = 2)
    @NotNull
    private String mnemonic;

    @JoinColumn(name = "ID_ZONESIRUTA_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Zonesiruta id_zonesiruta;

    public Zonesiruta getId_zonesiruta() {
        return id_zonesiruta;
    }

    public void setId_zonesiruta(Zonesiruta id_zonesiruta) {
        this.id_zonesiruta = id_zonesiruta;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getSiruta() {
        return siruta;
    }

    public void setSiruta(Integer siruta) {
        this.siruta = siruta;
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