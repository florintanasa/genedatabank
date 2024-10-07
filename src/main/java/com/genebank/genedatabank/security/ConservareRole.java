/**
 * @author : Florin Tanasă
 * @since : 19.08.2024
 **/

package com.genebank.genedatabank.security;

import com.genebank.genedatabank.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Conservare", code = ConservareRole.CODE)
public interface ConservareRole {
    String CODE = "conservare";

    @MenuPolicy(menuIds = {"Taxonomy.list", "Pasaport.list", "Deposit.list", "SysFile.list", "Aegisstat.list", "Collsrc.list", "Historic.list", "Georefmeth.list", "Acceconf.list", "Mlsstat.list", "Sampstat.list", "Culturecateg.list", "Curators.list", "Scope_.list", "TypeDocuments.list", "Storage.list", "Country.list", "Zonesiruta.list", "Countysiruta.list", "Localitysiruta.list", "Roadtype.list", "Street.list", "Partners.list", "Institute.list", "Duplicate.list", "DuplicateLine.list"})
    @ViewPolicy(viewIds = {"Taxonomy.list", "Pasaport.list", "Deposit.list", "SysFile.list", "Aegisstat.list", "Collsrc.list", "Historic.list", "Georefmeth.list", "Acceconf.list", "Mlsstat.list", "Sampstat.list", "Culturecateg.list", "Curators.list", "Scope_.list", "TypeDocuments.list", "Storage.list", "Country.list", "Zonesiruta.list", "Countysiruta.list", "Localitysiruta.list", "Roadtype.list", "Street.list", "Partners.list", "Institute.list", "Acceconf.detail", "Aegisstat.detail", "Collsrc.detail", "Country.detail", "Countysiruta.detail", "Culturecateg.detail", "Curators.detail", "Deposit.detail", "Georefmeth.detail", "Historic.detail", "Institute.detail", "Localitysiruta.detail", "Mlsstat.detail", "Partners.detail", "Pasaport.detail", "Roadtype.detail", "Sampstat.detail", "Scope_.detail", "Storage.detail", "Street.detail", "SysFile.detail", "Taxonomy.detail", "TypeDocuments.detail", "inputDialog", "Duplicate.list", "Duplicate.detail", "DuplicateLine.detail", "DuplicateLine.list"})
    void screens();

    @EntityAttributePolicy(entityClass = Deposit.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Deposit.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void deposit();

    @EntityPolicy(entityClass = Acceconf.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Acceconf.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void acceconf();

    @EntityAttributePolicy(entityClass = Aegisstat.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Aegisstat.class, actions = EntityPolicyAction.READ)
    void aegisstat();

    @EntityAttributePolicy(entityClass = Collsrc.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Collsrc.class, actions = EntityPolicyAction.READ)
    void collsrc();

    @EntityAttributePolicy(entityClass = Country.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Country.class, actions = EntityPolicyAction.READ)
    void country();

    @EntityAttributePolicy(entityClass = Countysiruta.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Countysiruta.class, actions = EntityPolicyAction.READ)
    void countysiruta();

    @EntityAttributePolicy(entityClass = Culturecateg.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Culturecateg.class, actions = EntityPolicyAction.READ)
    void culturecateg();

    @EntityAttributePolicy(entityClass = Curators.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Curators.class, actions = EntityPolicyAction.READ)
    void curators();

    @EntityAttributePolicy(entityClass = Georefmeth.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Georefmeth.class, actions = EntityPolicyAction.READ)
    void georefmeth();

    @EntityAttributePolicy(entityClass = Historic.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Historic.class, actions = EntityPolicyAction.READ)
    void historic();

    @EntityAttributePolicy(entityClass = Institute.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Institute.class, actions = EntityPolicyAction.READ)
    void institute();

    @EntityAttributePolicy(entityClass = Localitysiruta.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Localitysiruta.class, actions = EntityPolicyAction.READ)
    void localitysiruta();

    @EntityAttributePolicy(entityClass = Mlsstat.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Mlsstat.class, actions = EntityPolicyAction.READ)
    void mlsstat();

    @EntityAttributePolicy(entityClass = Pasaport.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Pasaport.class, actions = EntityPolicyAction.READ)
    void pasaport();

    @EntityAttributePolicy(entityClass = Partners.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Partners.class, actions = EntityPolicyAction.READ)
    void partners();

    @EntityAttributePolicy(entityClass = Roadtype.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Roadtype.class, actions = EntityPolicyAction.READ)
    void roadtype();

    @EntityAttributePolicy(entityClass = Sampstat.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Sampstat.class, actions = EntityPolicyAction.READ)
    void sampstat();

    @EntityAttributePolicy(entityClass = Scope.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Scope.class, actions = EntityPolicyAction.READ)
    void scope();

    @EntityAttributePolicy(entityClass = Storage.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Storage.class, actions = EntityPolicyAction.READ)
    void storage();

    @EntityAttributePolicy(entityClass = Street.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Street.class, actions = EntityPolicyAction.READ)
    void street();

    @EntityAttributePolicy(entityClass = SysFile.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SysFile.class, actions = EntityPolicyAction.READ)
    void sysFile();

    @EntityAttributePolicy(entityClass = Taxonomy.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Taxonomy.class, actions = EntityPolicyAction.READ)
    void taxonomy();

    @EntityAttributePolicy(entityClass = TypeDocuments.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = TypeDocuments.class, actions = EntityPolicyAction.READ)
    void typeDocuments();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @EntityAttributePolicy(entityClass = Zonesiruta.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Zonesiruta.class, actions = EntityPolicyAction.READ)
    void zonesiruta();

    @EntityAttributePolicy(entityClass = Duplicate.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Duplicate.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void duplicate();

    @EntityAttributePolicy(entityClass = DuplicateLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = DuplicateLine.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void duplicateLine();
}