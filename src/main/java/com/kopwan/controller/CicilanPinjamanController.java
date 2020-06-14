package com.kopwan.controller;

import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.request.param.CicilanPinjamanParam;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.model.response.RestListResponse;
import com.kopwan.model.response.cicilan.CicilanPinjamanResponse;
import com.kopwan.service.CicilanPinjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CicilanPinjamanController extends BaseController {
    @Autowired
    private CicilanPinjamanService service;

    @GetMapping(value = ApiPath.CICILAN,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestListResponse<CicilanPinjamanResponse>> filterCicilan(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "999") int rw,
            @RequestParam(defaultValue = "999") int no,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String month,
            @RequestParam(defaultValue = "999") int year) {
        return service.filterCicilan(createParamRequest(page, size, no, name, month, year, rw))
                .map(data -> toListResponse(createResponseList(data.getContent()),
                        buildPageMetaData(page, size, data.getTotalElements())))
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.CICILAN_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RestBaseResponse> deleteCicilanPinjaman(@PathVariable String id) {
        return service.deleteCicilanPinjaman(id)
                .thenReturn(toBaseResponse())
                .doOnError(this::handleError)
                .subscribeOn(Schedulers.elastic());
    }

    private List<CicilanPinjamanResponse> createResponseList(List<CicilanPinjaman> cicilanList) {
        return cicilanList.stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
    }

    private CicilanPinjamanResponse createResponse(CicilanPinjaman cicilan) {
        return CicilanPinjamanResponse.builder()
                .id(cicilan.getId())
                .anggota(cicilan.getAnggota())
                .pokok(cicilan.getPokok())
                .jasa(cicilan.getJasa())
                .month(cicilan.getMonth())
                .year(cicilan.getYear())
                .build();
    }

    private CicilanPinjamanParam createParamRequest(int page, int size, int no, String name,
                                                    String month, int year, int rw){
        buildPageRequest(page, size);
        return CicilanPinjamanParam.builder()
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
