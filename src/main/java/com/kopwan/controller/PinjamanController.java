package com.kopwan.controller;

import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.request.PinjamanRequest;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestSingleResponse;
import com.kopwan.model.response.pinjaman.PinjamanResponse;
import com.kopwan.service.PinjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

    @DeleteMapping(value = ApiPath.PINJAMAN_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> deletePinjaman(@PathVariable String id) {
        return pinjamanService.deletePinjaman(id)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
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
}
