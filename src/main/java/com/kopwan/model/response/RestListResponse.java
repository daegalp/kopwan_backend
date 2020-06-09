package com.kopwan.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class RestListResponse<T> extends RestBaseResponse {
    private List<T> content;
    private PageMetaData pageMetaData = new PageMetaData();

    public RestListResponse(RestBaseResponse baseResponse) {
        setRequestId(baseResponse.getRequestId());
        setErrorCode(baseResponse.getErrorCode());
        setErrorMessage(baseResponse.getErrorMessage());
        setSuccess(baseResponse.isSuccess());
    }
}

