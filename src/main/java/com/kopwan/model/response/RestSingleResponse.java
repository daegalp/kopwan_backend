package com.kopwan.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestSingleResponse<T> extends RestBaseResponse {
    private T content;

    public RestSingleResponse(RestBaseResponse baseResponse) {
        setRequestId(baseResponse.getRequestId());
        setErrorCode(baseResponse.getErrorCode());
        setErrorMessage(baseResponse.getErrorMessage());
        setSuccess(baseResponse.isSuccess());
    }
}

