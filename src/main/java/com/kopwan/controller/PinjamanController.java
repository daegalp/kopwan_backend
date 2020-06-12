package com.kopwan.controller;

import com.kopwan.model.constant.ApiPath;
import com.kopwan.model.request.PinjamanRequest;
import com.kopwan.model.response.RestBaseResponse;
import com.kopwan.service.PinjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
