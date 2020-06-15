package com.kopwan.controller;

import com.kopwan.model.entity.Anggota;
import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.request.AnggotaRequest;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestListResponse;
import com.kopwan.model.response.RestSingleResponse;
import com.kopwan.model.response.anggota.AnggotaResponse;
import com.kopwan.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnggotaController extends BaseController{
    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(value = ApiPath.ANGGOTA_BY_NO,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<AnggotaResponse>> getAnggota(@PathVariable String no) {
        return anggotaService.findByNoAnggota(no)
                .map(this::createAnggotaResponse)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.ANGGOTA,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> createAnggota(@RequestBody AnggotaRequest request) {
        return anggotaService.createAnggota(request)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.ANGGOTA,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestListResponse<AnggotaResponse>> getAllAnggota(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return anggotaService.findAllAnggota(buildPageRequest(page, size))
                .map(data -> toListResponse(createAnggotaResponseList(data.getContent()),
                        buildPageMetaData(page, size, data.getTotalElements())))
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.ANGGOTA,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<AnggotaResponse>> updateAnggota(@RequestBody AnggotaRequest request) {
        return anggotaService.updateAnggota(request)
                .map(this::createAnggotaResponse)
                .map(this::toSingleResponse)
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.ANGGOTA_BY_NO,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> deleteAnggota(@PathVariable String no) {
        return anggotaService.deleteAnggota(no)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    private List<AnggotaResponse> createAnggotaResponseList(List<Anggota> anggotaList) {
        return anggotaList.stream()
                .map(this::createAnggotaResponse)
                .collect(Collectors.toList());
    }

    private AnggotaResponse createAnggotaResponse(Anggota anggota) {
        return AnggotaResponse.builder()
                .no(anggota.getNo())
                .name(anggota.getName())
                .rw(anggota.getRw())
                .build();
    }
}
