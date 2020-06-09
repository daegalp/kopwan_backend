package com.kopwan.dao;

import com.kopwan.model.Anggota;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AnggotaRepository extends ReactiveMongoRepository<Anggota, String> {
    Mono<Anggota> findByNameAndMarkForDeleteFalse(String name);
    Flux<Anggota> findAllByMarkForDeleteFalse();
}
