package com.kopwan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopwan.controller.util.PaginationUtil;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.BaseException;
import com.kopwan.model.response.PageMetaData;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestListResponse;
import com.kopwan.model.response.RestSingleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.UUID;

public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    ObjectMapper mapper;
    @Autowired
    private PaginationUtil paginationUtil;

    <T> RestListResponse<T> toListResponse(List<T> data) {
        return RestListResponse.<T>builder()
                .requestId(UUID.randomUUID().toString())
                .success(true)
                .content(data)
                .build();
    }

    <T> RestListResponse<T> toListResponse(List<T> data, PageMetaData pageMetaData) {
        return RestListResponse.<T>builder()
                .requestId(UUID.randomUUID().toString())
                .success(true)
                .content(data)
                .pageMetaData(pageMetaData)
                .build();
    }

    public <T> RestSingleResponse<T> toSingleResponse(T data) {
        return RestSingleResponse.<T>builder()
                .requestId(UUID.randomUUID().toString())
                .success(true)
                .content(data)
                .build();
    }

    public RestBaseResponse toBaseResponse() {
        RestBaseResponse restBaseResponse = new RestBaseResponse();
        restBaseResponse.setRequestId(UUID.randomUUID().toString());
        restBaseResponse.setSuccess(true);
        return restBaseResponse;
    }

    protected PageMetaData buildPageMetaData(long pageNumber, long pageSize, long totalRecords) {
        return PageMetaData.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalRecords(totalRecords)
                .build();
    }

    protected Pageable buildPageRequest(int page, int size){
        paginationUtil.validatePageRequest(page, size);
        return PageRequest.of(page - 1, size);
    }

    public <T> void handleError(Throwable e) {
        BaseException ge;
        if (e instanceof BaseException) {
            ge = (BaseException) e;
        } else if (e instanceof WebClientResponseException) {
            ge = this.constructExceptionFromWebClientException((WebClientResponseException) e);
        } else {
            ge = new BaseException(ErrorCode.UNSPECIFIED_ERROR, e);
        }
        ge.setRequestId(UUID.randomUUID().toString());
        ge.setExceptionType(e.getClass().getSimpleName());
        throw ge;
    }

    private BaseException constructExceptionFromWebClientException(WebClientResponseException e) {
        RestBaseResponse response = null;
        try {
            response = mapper.readValue(e.getResponseBodyAsString(), RestBaseResponse.class);
        } catch (Exception error) {
            LOGGER.error("Error Parsing RestBaseResponse", error);
        }
        if (response != null) {
            return new BaseException(response.getErrorCode(), response.getErrorMessage(),
                    e.getStatusCode().value());
        }
        return new BaseException(ErrorCode.UNSPECIFIED_ERROR, e);
    }
}
