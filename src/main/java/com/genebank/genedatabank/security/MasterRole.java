package com.genebank.genedatabank.security;

import com.genebank.genedatabank.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Master", code = MasterRole.CODE)
public interface MasterRole {
    String CODE = "master";

    @MenuPolicy(menuIds = {"Taxonomy.list", "Pasaport.list", "Deposit.list", "SysFile.list", "Aegisstat.list", "Collsrc.list", "Historic.list", "Georefmeth.list", "Acceconf.list", "Mlsstat.list", "Sampstat.list", "Culturecateg.list", "Curators.list", "Scope_.list", "TypeDocuments.list", "Storage.list", "Country.list", "Zonesiruta.list", "Countysiruta.list", "Localitysiruta.list", "Roadtype.list", "Street.list", "Partners.list", "Institute.list", "Duplicate.list"})
    @ViewPolicy(viewIds = {"Taxonomy.list", "Pasaport.list", "Deposit.list", "SysFile.list", "Aegisstat.list", "Collsrc.list", "Historic.list", "Georefmeth.list", "Acceconf.list", "Mlsstat.list", "Sampstat.list", "Culturecateg.list", "Curators.list", "Scope_.list", "TypeDocuments.list", "Storage.list", "Country.list", "Zonesiruta.list", "Countysiruta.list", "Localitysiruta.list", "Roadtype.list", "Street.list", "Partners.list", "Institute.list", "Taxonomy.detail", "Zonesiruta.detail", "TypeDocuments.detail", "SysFile.detail", "Street.detail", "Storage.detail", "Scope_.detail", "Sampstat.detail", "Roadtype.detail", "Pasaport.detail", "Partners.detail", "Mlsstat.detail", "Localitysiruta.detail", "Institute.detail", "Historic.detail", "Georefmeth.detail", "Deposit.detail", "Curators.detail", "Culturecateg.detail", "Countysiruta.detail", "Country.detail", "Collsrc.detail", "Aegisstat.detail", "Acceconf.detail", "inputDialog", "Duplicate.list", "Duplicate.detail", "DuplicateLine.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Acceconf.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Acceconf.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void acceconf();

    @EntityAttributePolicy(entityClass = Aegisstat.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Aegisstat.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void aegisstat();

    @EntityAttributePolicy(entityClass = Collsrc.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Collsrc.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void collsrc();

    @EntityAttributePolicy(entityClass = Country.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Country.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void country();

    @EntityAttributePolicy(entityClass = Countysiruta.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Countysiruta.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void countysiruta();

    @EntityAttributePolicy(entityClass = Culturecateg.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Culturecateg.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void culturecateg();

    @EntityAttributePolicy(entityClass = Curators.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Curators.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void curators();

    @EntityAttributePolicy(entityClass = Deposit.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Deposit.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void deposit();

    @EntityAttributePolicy(entityClass = Georefmeth.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Georefmeth.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void georefmeth();

    @EntityAttributePolicy(entityClass = Historic.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Historic.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void historic();

    @EntityAttributePolicy(entityClass = Institute.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Institute.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void institute();

    @EntityAttributePolicy(entityClass = Localitysiruta.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Localitysiruta.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void localitysiruta();

    @EntityAttributePolicy(entityClass = Mlsstat.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Mlsstat.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void mlsstat();

    @EntityAttributePolicy(entityClass = Partners.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Partners.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void partners();

    @EntityAttributePolicy(entityClass = Pasaport.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Pasaport.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void pasaport();

    @EntityAttributePolicy(entityClass = Roadtype.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Roadtype.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void roadtype();

    @EntityAttributePolicy(entityClass = Sampstat.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Sampstat.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void sampstat();

    @EntityAttributePolicy(entityClass = Scope.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Scope.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void scope();

    @EntityAttributePolicy(entityClass = Storage.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Storage.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void storage();

    @EntityAttributePolicy(entityClass = Street.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Street.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void street();

    @EntityAttributePolicy(entityClass = SysFile.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SysFile.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void sysFile();

    @EntityAttributePolicy(entityClass = Taxonomy.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Taxonomy.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void taxonomy();

    @EntityAttributePolicy(entityClass = TypeDocuments.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TypeDocuments.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void typeDocuments();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void user();

    @EntityAttributePolicy(entityClass = Zonesiruta.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Zonesiruta.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void zonesiruta();

    @EntityAttributePolicy(entityClass = Duplicate.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Duplicate.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE, EntityPolicyAction.READ})
    void duplicate();

    @EntityAttributePolicy(entityClass = DuplicateLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = DuplicateLine.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void duplicateLine();
}