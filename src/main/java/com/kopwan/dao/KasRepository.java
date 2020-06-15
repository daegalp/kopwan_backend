package com.kopwan.dao;

import com.kopwan.model.entity.Kas;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface KasRepository extends ReactiveMongoRepository<Kas, String> {
    Mono<Kas> findByIdAndMarkForDeleteFalse(String id);
}
