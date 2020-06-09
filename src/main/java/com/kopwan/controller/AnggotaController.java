package com.kopwan.controller;

import com.kopwan.model.Anggota;
import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.enums.FeatureCode;
import com.kopwan.model.response.RestListResponse;
import com.kopwan.model.response.RestSingleResponse;
import com.kopwan.model.response.anggota.AnggotaResponse;
import com.kopwan.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnggotaController extends BaseController{
    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(value = ApiPath.GET_ANGGOTA_BY_NAME,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestSingleResponse<AnggotaResponse>> getAnggota(@PathVariable String name) {
        return anggotaService.findAnggotaByName(name)
                .map(this::createAnggotaResponse)
                .map(this::toSingleResponse)
                .doOnError(e -> handleError(FeatureCode.GET_ANGGOTA_BY_NAME, e))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.GET_ALL_ANGGOTA,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestListResponse<AnggotaResponse>> getAllAnggota() {
        return anggotaService.findAllAnggota()
                .map(this::createAnggotaResponseList)
                .map(this::toListResponse)
                .doOnError(e -> handleError(FeatureCode.GET_ANGGOTA_BY_NAME, e))
                .subscribeOn(Schedulers.elastic());
    }

    private List<AnggotaResponse> createAnggotaResponseList(List<Anggota> anggotaList) {
        return anggotaList.stream()
                .map(this::createAnggotaResponse)
                .collect(Collectors.toList());
    }

    private AnggotaResponse createAnggotaResponse(Anggota anggota) {
        return AnggotaResponse.builder()
                .name(anggota.getName())
                .build();
    }
}
