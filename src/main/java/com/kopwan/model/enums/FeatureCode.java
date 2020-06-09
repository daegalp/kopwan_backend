package com.kopwan.model.enums;

public enum  FeatureCode {
    GET_ANGGOTA_BY_NAME("Get Anggota by name");

    private final String description;

    FeatureCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
