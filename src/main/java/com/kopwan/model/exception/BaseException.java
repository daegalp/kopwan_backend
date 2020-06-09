package com.kopwan.model.exception;

import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.enums.FeatureCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -7308323645471082816L;

    private String requestId;
    private String exceptionType;
    private FeatureCode featureCode;
    private String code;
    private String message;
    private int httpStatus;

    public BaseException(ErrorCode errorCode, Throwable e) {
        super(e);
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getDescription();
        this.httpStatus = errorCode.getHttpStatus();
        this.exceptionType = e.getClass().getSimpleName();
    }

    public BaseException(ErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getDescription();
        this.httpStatus = errorCode.getHttpStatus();
        this.exceptionType = getClass().getSimpleName();
    }

    public BaseException(ErrorCode errorCode, String requestId) {
        this.requestId = requestId;
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getDescription();
        this.httpStatus = errorCode.getHttpStatus();
        this.exceptionType = getClass().getSimpleName();
    }

    public BaseException(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.exceptionType = getClass().getSimpleName();
    }
}

