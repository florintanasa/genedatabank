package com.genebank.genedatabank.security;

import com.genebank.genedatabank.entity.Pasaport;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Pasaport confidential", code = PasaportConfidentialRole.CODE)
public interface PasaportConfidentialRole {
    String CODE = "pasaport-confidential-r1";

    @JpqlRowLevelPolicy(entityClass = Pasaport.class, where = "{E}.id_acceconf.code = 0")
    void pasaport();
}