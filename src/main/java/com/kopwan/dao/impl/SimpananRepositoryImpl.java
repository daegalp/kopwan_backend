package com.kopwan.dao.impl;

import com.kopwan.dao.SimpananRepositoryCustom;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.request.param.SimpananParamRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SimpananRepositoryImpl implements SimpananRepositoryCustom {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Page<Simpanan>> filterSimpanan(SimpananParamRequest request) {
        Query query = new Query();
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1,
                request.getSize());

        query.addCriteria(Criteria.where("markForDelete").is(false));
        if (StringUtils.isNotBlank(request.getType())) {
            query.addCriteria(Criteria.where("type")
                    .is(request.getType()));
        }
        if (StringUtils.isNotBlank(request.getMonth())) {
            query.addCriteria(Criteria.where("month")
                    .is(request.getMonth()));
        }
        if(request.getRw() != 999){
            query.addCriteria(Criteria.where("anggota.rw")
                    .is(request.getRw()));
        }
        if(request.getNo() != 999){
            query.addCriteria(Criteria.where("anggota.no")
                    .is(request.getNo()));
        }
        if(request.getYear() != 999){
            query.addCriteria(Criteria.where("year")
                    .is(request.getYear()));
        }
        if (StringUtils.isNotBlank(request.getMonth()) || request.getYear() != 999) {
            query.with(Sort.by(Sort.Direction.ASC, "anggota.rw"));
        }
        return findAllPageable(query, Simpanan.class, pageRequest);
    }

    public <T> Mono<Page<T>> findAllPageable(Query query, Class<T> entity, PageRequest pageRequest) {
        return reactiveMongoTemplate.count(query, entity)
                .flatMap(count -> findAll(count, pageRequest, query, entity));
    }

    private <T> Mono<Page<T>> findAll(Long totalRecords, Pageable pageable,
                                      Query query, Class<T> entity) {
        query.with(pageable);
        return reactiveMongoTemplate.find(query, entity)
                .collectList()
                .map(data -> new PageImpl<>(data, pageable, totalRecords));
    }
}
