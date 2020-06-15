package com.kopwan.controller;

import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.request.CicilanRequest;
import com.kopwan.model.request.PinjamanRequest;
import com.kopwan.model.request.param.PinjamanParamRequest;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestListResponse;
import com.kopwan.model.response.RestSingleResponse;
import com.kopwan.model.response.pinjaman.PinjamanResponse;
import com.kopwan.service.PinjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PinjamanController extends BaseController {
    @Autowired
    private PinjamanService pinjamanService;

    @PostMapping(value = ApiPath.PINJAMAN,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> createPinjaman(@RequestBody PinjamanRequest request) {
        return pinjamanService.createPinjaman(request)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PINJAMAN,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestListResponse<PinjamanResponse>> filterPinjaman(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int rw,
            @RequestParam(defaultValue = "0") int no,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int year) {
        return pinjamanService.filterPinjaman(createParamRequest(page, size, no, name, month, year, rw))
                .map(data -> toListResponse(createPinjamanResponseList(data.getContent()),
                        buildPageMetaData(page, size, data.getTotalElements())))
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.PINJAMAN_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<PinjamanResponse>> updatePinjaman(
            @PathVariable String id,
            @RequestBody PinjamanRequest request) {
        return pinjamanService.updatePinjaman(id, request)
                .map(this::createPinjamanResponse)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.PINJAMAN,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> updateLunas(@RequestBody CicilanRequest request) {
        return pinjamanService.updateLunas(request)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.PINJAMAN_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> deletePinjaman(@PathVariable String id) {
        return pinjamanService.deletePinjaman(id)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    private List<PinjamanResponse> createPinjamanResponseList(List<Pinjaman> pinjamanList) {
        return pinjamanList.stream()
                .map(this::createPinjamanResponse)
                .collect(Collectors.toList());
    }

    private PinjamanResponse createPinjamanResponse(Pinjaman pinjaman) {
        return PinjamanResponse.builder()
                .id(pinjaman.getId())
                .anggota(pinjaman.getAnggota())
                .nominal(pinjaman.getNominal())
                .lunas(pinjaman.isLunas())
                .month(pinjaman.getMonth())
                .year(pinjaman.getYear())
                .actual(pinjaman.getActual())
                .target(pinjaman.getTarget())
                .build();
    }

    private PinjamanParamRequest createParamRequest(int page, int size, int no, String name,
                                                    int month, int year, int rw){
        buildPageRequest(page, size);
        return PinjamanParamRequest.builder()
                .page(page)
                .size(size)
                .rw(rw)
                .no(no)
                .name(name)
                .month(month)
                .year(year)
                .build();
    }
}
