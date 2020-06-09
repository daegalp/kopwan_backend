package com.kopwan.model.enums;

import java.util.Arrays;

public enum ErrorCode {
    NAME_NOT_FOUND("ERR-PA404001",
            404, "Cannot find name"),
    UNSPECIFIED_ERROR("ERR-PI500001",500,
            "Unspecified error that not handled by generic handler");

    private final int httpStatus;
    private final String description;
    private final String errorCode;

    ErrorCode(String errorCode, int httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = description;
        this.errorCode = errorCode;
    }

    public static ErrorCode findByErrorCode(String errorCode) {
        return Arrays.stream(ErrorCode.values())
                .filter(error -> error.getErrorCode().equals(errorCode))
                .findAny()
                .orElse(null);
    }

    public String getDescription() {
        return description;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}