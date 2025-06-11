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
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
 **/

@JmixEntity
@Table(name = "TAXONOMY")
@Entity
public class Taxonomy {
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

    @Column(name = "FAMILY", length = 20)
    private String family;

    @Column(name = "GENUS", length = 20)
    private String genus;

    @Column(name = "SPECIES", length = 90)
    private String species;

    @Column(name = "SPAUTHOR", length = 50)
    private String spauthor;

    @Column(name = "SUBTAXA", length = 55)
    private String subtaxa;

    @Column(name = "SUBAUTHOR", length = 50)
    private String subauthor;

    @Column(name = "SYN_TAXONO")
    private String syn_taxono;

    @Column(name = "CROPNUME", length = 110)
    private String cropnume;

    @Column(name = "CROPNAME", length = 110)
    private String cropname;

    @JoinTable(name = "TAXONOMY_CULTURECATEG_LINK",
            joinColumns = @JoinColumn(name = "TAXONOMY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CULTURECATEG_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Culturecateg> id_culturecateg;

    @Column(name = "REFFERENCE")
    private String refference;

    public String getRefference() {
        return refference;
    }

    public void setRefference(String refference) {
        this.refference = refference;
    }

    public List<Culturecateg> getId_culturecateg() {
        return id_culturecateg;
    }

    public void setId_culturecateg(List<Culturecateg> id_culturecateg) {
        this.id_culturecateg = id_culturecateg;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getCropnume() {
        return cropnume;
    }

    public void setCropnume(String cropnume) {
        this.cropnume = cropnume;
    }

    public String getSyn_taxono() {
        return syn_taxono;
    }

    public void setSyn_taxono(String syn_taxono) {
        this.syn_taxono = syn_taxono;
    }

    public String getSubauthor() {
        return subauthor;
    }

    public void setSubauthor(String subauthor) {
        this.subauthor = subauthor;
    }

    public String getSubtaxa() {
        return subtaxa;
    }

    public void setSubtaxa(String subtaxa) {
        this.subtaxa = subtaxa;
    }

    public String getSpauthor() {
        return spauthor;
    }

    public void setSpauthor(String spauthor) {
        this.spauthor = spauthor;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
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
    @DependsOnProperties({"family", "genus", "species", "spauthor", "subtaxa", "subauthor", "syn_taxono", "cropnume", "cropname","id_culturecateg"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s %s %s %s %s %s %s %s %s %s",
                metadataTools.format(family),
                metadataTools.format(genus),
                metadataTools.format(species),
                metadataTools.format(spauthor),
                metadataTools.format(subtaxa),
                metadataTools.format(subauthor),
                metadataTools.format(syn_taxono),
                metadataTools.format(cropnume),
                metadataTools.format(cropname),
                metadataTools.format(id_culturecateg));
    }
}