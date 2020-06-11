package com.kopwan.dao;

import com.kopwan.model.entity.Simpanan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SimpananRepository extends ReactiveMongoRepository<Simpanan, String>, SimpananRepositoryCustom {
    Mono<Simpanan> findByIdAndMarkForDeleteFalse(String id);
}
