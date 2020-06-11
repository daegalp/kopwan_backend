package com.kopwan.controller;

import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.request.SimpananRequest;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestSingleResponse;
import com.kopwan.model.response.simpanan.SimpananDetailResponse;
import com.kopwan.service.SimpananService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SimpananController extends BaseController {
    @Autowired
    private SimpananService simpananService;

    @PostMapping(value = ApiPath.SIMPANAN,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> createSimpanan(@RequestBody SimpananRequest request) {
        return simpananService.createSimpanan(request)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.GET_SIMPANAN_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<SimpananDetailResponse>> getAnggota(@PathVariable String id) {
        return simpananService.getDetailSimpanan(id)
                .map(this::createDetailResponse)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    private List<SimpananDetailResponse> createDetailResponseList(List<Simpanan> simpananList) {
        return simpananList.stream()
                .map(this::createDetailResponse)
                .collect(Collectors.toList());
    }

    private SimpananDetailResponse createDetailResponse(Simpanan simpanan) {
        return SimpananDetailResponse.builder()
                .id(simpanan.getId())
                .anggota(simpanan.getAnggota())
                .type(simpanan.getType())
                .nominal(simpanan.getNominal())
                .month(simpanan.getMonth())
                .year(simpanan.getYear())
                .build();
    }
}