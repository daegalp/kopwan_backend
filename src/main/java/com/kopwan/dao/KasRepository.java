package com.kopwan.dao;

import com.kopwan.model.entity.Kas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface KasRepository extends ReactiveMongoRepository<Kas, String> {
    Mono<Kas> findByIdAndMarkForDeleteFalse(String id);
    Flux<Kas> findAllByMonthAndYearAndMarkForDeleteFalse(int month, int year, Pageable pageable);

    Mono<Long> countAllByMonthAndYearAndMarkForDeleteFalse(int month, int year);
}
