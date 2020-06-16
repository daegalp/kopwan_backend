package com.kopwan.controller;

import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.entity.Kas;
import com.kopwan.model.request.KasRequest;
import com.kopwan.model.request.param.KasParamRequest;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestListResponse;
import com.kopwan.model.response.RestSingleResponse;
import com.kopwan.model.response.kas.KasResponse;
import com.kopwan.model.response.kas.TotalKasResponse;
import com.kopwan.service.KasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class KasController extends BaseController {
    @Autowired
    private KasService kasService;

    @PostMapping(value = ApiPath.KAS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> createKas(@RequestBody KasRequest request) {
        return kasService.createKas(request)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.KAS_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<KasResponse>> getKasById(@PathVariable String id) {
        return kasService.getById(id)
                .map(this::createKasResponse)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.KAS_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<KasResponse>> updateKas(
            @PathVariable String id,
            @RequestBody KasRequest request) {
        return kasService.updateKas(id, request)
                .map(this::createKasResponse)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.KAS_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> deleteKas(@PathVariable String id) {
        return kasService.deleteKas(id)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.GENERATE_KAS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> generateKasBulanan(@RequestBody KasParamRequest request) {
        return kasService.generateBukuKas(request)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.KAS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestListResponse<KasResponse>> getAllByMonthAndYear(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int year) {
        return kasService.findAllByMonthAndYear(month, year, buildPageRequest(page, size))
                .map(data -> toListResponse(createKasResponseList(data.getContent()),
                        buildPageMetaData(page, size, data.getTotalElements())))
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.KAS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> deleteAllKasByMonthAndYear(
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int year) {
        return kasService.deleteAllKasByMonthAndYear(month, year)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.KAS_TOTAL,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<TotalKasResponse>> getTotalByMonthAndYear(
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int year) {
        return kasService.getTotalKasByMonthAndYear(month, year)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    private List<KasResponse> createKasResponseList(List<Kas> kasList) {
        return kasList.stream()
                .map(this::createKasResponse)
                .collect(Collectors.toList());
    }

    private KasResponse createKasResponse(Kas kas) {
        return KasResponse.builder()
                .id(kas.getId())
                .name(kas.getName())
                .alurKas(kas.getAlurKas())
                .type(kas.getType())
                .nominal(kas.getNominal())
                .month(kas.getMonth())
                .year(kas.getYear())
                .build();
    }
}
