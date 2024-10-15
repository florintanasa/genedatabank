package com.genebank.genedatabank.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum ViabilityStatus implements EnumClass<String> {

    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    private final String id;

    ViabilityStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ViabilityStatus fromId(String id) {
        for (ViabilityStatus at : ViabilityStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}