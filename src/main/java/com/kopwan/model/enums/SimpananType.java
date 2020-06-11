package com.kopwan.model.enums;

import com.kopwan.model.exception.DataNotFoundException;

public enum SimpananType {
    POKOK("POKOK"),
    WAJIB("WAJIB"),
    SUKARELA("SUKARELA"),
    DUKA("DUKA");

    private final String description;

    SimpananType(String description) {
        this.description = description;
    }

    public static SimpananType validateType(String simpananType) {
        try {
            return SimpananType.valueOf(simpananType.toUpperCase());
        } catch (Exception e) {
            throw new DataNotFoundException(ErrorCode.INVALID_SIMPANAN_TYPE);
        }
    }

    public String getDescription() {
        return description;
    }
}
