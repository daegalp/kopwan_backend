package com.kopwan.dao;

import com.kopwan.model.entity.Anggota;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AnggotaRepository extends ReactiveMongoRepository<Anggota, String> {
    Mono<Anggota> findByNoAndMarkForDeleteFalse(String no);
    Flux<Anggota> findAllByMarkForDeleteFalseOrderByRw(Pageable pageable);

    Mono<Long> countByMarkForDeleteFalse();
}
