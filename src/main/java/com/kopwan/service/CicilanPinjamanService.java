package com.kopwan.service;

import com.kopwan.dao.CicilanPinjamanRepository;
import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.request.CicilanRequest;
import com.kopwan.model.request.param.CicilanPinjamanParam;
import com.kopwan.service.util.CicilanPinjamanServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CicilanPinjamanService {
    @Autowired
    private CicilanPinjamanRepository repository;
    @Autowired
    private CicilanPinjamanServiceUtil util;
    @Autowired
    private PinjamanService pinjamanService;

    public Mono<Page<CicilanPinjaman>> filterCicilan(CicilanPinjamanParam request){
        return repository.filterCicilanPinjaman(request);
    }

    public Mono<Void> createCicilan(CicilanRequest request) {
        return pinjamanService.findByNoAnggota(request.getNo())
                .flatMap(result -> repository.save(util.convertToCicilan(request, result)))
                .flatMap(result -> pinjamanService.updateActual(request.getNo()))
                .then();
    }

    public Mono<Void> deleteCicilanPinjaman(String id) {
        return repository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.CICILAN_NOT_FOUND)))
                .flatMap(result -> repository.save(util.delete(result)))
                .then();
    }
}
