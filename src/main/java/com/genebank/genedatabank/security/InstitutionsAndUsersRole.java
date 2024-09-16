package com.genebank.genedatabank.security;

import com.genebank.genedatabank.entity.Deposit;
import com.genebank.genedatabank.entity.Institute;
import com.genebank.genedatabank.entity.Pasaport;
import com.genebank.genedatabank.entity.User;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Institutions and users", code = InstitutionsAndUsersRole.CODE)
public interface InstitutionsAndUsersRole {
    String CODE = "institutions-users-r1";

    @JpqlRowLevelPolicy(entityClass = User.class, where = "{E}.id_institute = :current_user_id_institute")
    void user();

    @JpqlRowLevelPolicy(entityClass = Pasaport.class, where = "{E}.id_instcode = :current_user_id_institute")
    void pasaport();

    @JpqlRowLevelPolicy(entityClass = Deposit.class, where = "{E}.id_accenumb.id_instcode = :current_user_id_institute")
    void deposit();
}