package com.kopwan.dao;

import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.request.param.CicilanPinjamanParam;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CicilanPinjamanRepositoryCustom {
    Mono<Page<CicilanPinjaman>> filterCicilanPinjaman(CicilanPinjamanParam request);
}
