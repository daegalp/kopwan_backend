package com.kopwan.model.exception;

import com.kopwan.model.enums.ErrorCode;

public class DataNotFoundException extends BaseException {

    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
