package com.genebank.genedatabank.security;

import com.genebank.genedatabank.entity.*;
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

    @JpqlRowLevelPolicy(entityClass = Duplicate.class, where = "{E}.id_send_institute = :current_user_id_institute")
    void duplicate();

    @JpqlRowLevelPolicy(entityClass = DuplicateLine.class, where = "{E}.duplicate.id_send_institute = :current_user_id_institute")
    void duplicateLine();

    @JpqlRowLevelPolicy(entityClass = ViabNewSeeds.class, where = "{E}.id_accenumb.id_instcode = :current_user_id_institute")
    void viabNewSeeds();

    @JpqlRowLevelPolicy(entityClass = ViabOldSeeds.class, where = "{E}.id_deposit_code.id_accenumb.id_instcode = :current_user_id_institute")
    void viabOldSeeds();

    @JpqlRowLevelPolicy(entityClass = ViabOldSeedsLine.class, where = "{E}.viabOldSeeds.id_deposit_code.id_accenumb.id_instcode = :current_user_id_institute")
    void viabOldSeedsLine();

    @JpqlRowLevelPolicy(entityClass = ViabNewSeedsLine.class, where = "{E}.viabNewSeeds.id_accenumb.id_instcode = :current_user_id_institute")
    void viabNewSeedsLine();
}