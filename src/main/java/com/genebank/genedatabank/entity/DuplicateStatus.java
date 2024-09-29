package com.genebank.genedatabank.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

/**
 * @author : Florin Tanasă
 * @since : 27.09.2024
 **/

public enum DuplicateStatus implements EnumClass<String> {

    PREPARED("PREPARED"),
    CONFIRMED("CONFIRMED"),
    IN_DELIVERY("IN_DELIVERY"),
    DELIVERED("DELIVERED");

    private final String id;

    DuplicateStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DuplicateStatus fromId(String id) {
        for (DuplicateStatus at : DuplicateStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}