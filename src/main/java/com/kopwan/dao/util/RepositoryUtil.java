package com.kopwan.dao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RepositoryUtil {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

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
