package com.kopwan.controller.util;

import com.kopwan.model.response.RestBaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResponseUtil {
    public ResponseEntity<RestBaseResponse> buildErrorResponse(String errCode, String errMsg,
                                                               int httpStatus) {
        RestBaseResponse restBaseResponse = RestBaseResponse.parentBuilder()
                .requestId(UUID.randomUUID().toString())
                .errorCode(errCode)
                .errorMessage(errMsg)
                .success(false)
                .build();
        return ResponseEntity
                .status(httpStatus)
                .body(restBaseResponse);
    }
}
