package com.kopwan.service;

import com.kopwan.dao.CicilanPinjamanRepository;
import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.request.param.CicilanPinjamanParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CicilanPinjamanService {
    @Autowired
    private CicilanPinjamanRepository repository;

    public Mono<Page<CicilanPinjaman>> filterCicilan(CicilanPinjamanParam request){
        return repository.filterCicilanPinjaman(request);
    }
}
