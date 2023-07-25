package com.genebank.genedatabank.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "COUNTRY")
@Entity
public class Country {
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

    @NotEmpty(message = "{msg://com.genebank.genedatabank.entity/Country.code.validation.NotEmpty}")
    @NotBlank(message = "{msg://com.genebank.genedatabank.entity/Country.code.validation.NotBlank}")
    @Column(name = "CODE", nullable = false, length = 3, unique = true)
    @NotNull
    private String code;

    @NotBlank(message = "{msg://com.genebank.genedatabank.entity/Country.name.validation.NotBlank}")
    @NotEmpty(message = "{msg://com.genebank.genedatabank.entity/Country.name.validation.NotEmpty}")
    @InstanceName
    @Column(name = "NAME", nullable = false, length = 35, unique = true)
    @NotNull
    private String name;

    @Length(min = 2, max = 2)
    @NotEmpty(message = "{msg://com.genebank.genedatabank.entity/Country.alpha2.validation.NotEmpty}")
    @NotBlank(message = "{msg://com.genebank.genedatabank.entity/Country.alpha2.validation.NotBlank}")
    @Column(name = "ALPHA2", nullable = false, length = 2, unique = true)
    @NotNull
    private String alpha2;

    @Length(min = 3, max = 3)
    @NotEmpty(message = "{msg://com.genebank.genedatabank.entity/Country.alpha3.validation.NotEmpty}")
    @NotBlank(message = "{msg://com.genebank.genedatabank.entity/Country.alpha3.validation.NotBlank}")
    @Column(name = "ALPHA3", nullable = false, length = 3, unique = true)
    @NotNull
    private String alpha3;

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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