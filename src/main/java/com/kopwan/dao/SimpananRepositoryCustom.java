package com.kopwan.dao;

import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.request.param.SimpananParamRequest;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface SimpananRepositoryCustom {
    Mono<Page<Simpanan>> filterSimpanan(SimpananParamRequest request);
}
