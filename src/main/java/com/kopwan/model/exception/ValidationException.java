package com.kopwan.model.exception;

import com.kopwan.model.enums.ErrorCode;

public class ValidationException extends BaseException {
    private static final long serialVersionUID = -7107653644055456668L;

    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ValidationException(ErrorCode errorCode, String requestId) {
        super(errorCode, requestId);
    }
}

