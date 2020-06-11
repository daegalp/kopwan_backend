package com.kopwan.model.enums;

import java.util.Arrays;

public enum ErrorCode {
    INVALID_PAGINATION_OF_PAGE("ERR-PA400001", 400,
            "Input of page can't be lower or equals to zero. It should start from 1"),
    INVALID_PAGINATION_OF_SIZE("ERR-PA400002", 400,
            "Input of size can't be lower or equals to zero. It should start from 1"),
    INVALID_PAGINATION_OF_MAX_SIZE("ERR-PA400003", 400,
            "Input of size can't be more than maximum size. It should start from 1"),
    NO_ANGGOTA_NOT_UNIQUE("ERR-PA400004", 400, "No Anggota must be unique"),

    NAME_NOT_FOUND("ERR-PA404001",
            404, "Cannot find name"),
    NO_ANGGOTA_NOT_FOUND("ERR-PA404002",
            404, "Cannot find no anggota"),

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