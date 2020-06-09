package com.kopwan.controller;

import com.kopwan.controller.util.ResponseUtil;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.BaseException;
import com.kopwan.model.response.RestBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @Autowired
    private ResponseUtil responseUtil;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RestBaseResponse> handleGeneralException(BaseException e) {
        return responseUtil.buildErrorResponse(e.getCode(), e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestBaseResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.UNSPECIFIED_ERROR;
        return responseUtil.buildErrorResponse(errorCode.name(),
                errorCode.getDescription(), errorCode.getHttpStatus());
    }
}
