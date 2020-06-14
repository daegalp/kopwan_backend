package com.kopwan.dao;

import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.request.param.PinjamanParamRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PinjamanRepositoryCustom {
    Mono<Page<Pinjaman>> filterPinjaman(PinjamanParamRequest request);
}
