package com.kopwan.dao;

import com.kopwan.model.entity.CicilanPinjaman;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CicilanPinjamanRepository
        extends ReactiveMongoRepository<CicilanPinjaman, String>, CicilanPinjamanRepositoryCustom {
    Mono<CicilanPinjaman> findByIdAndMarkForDeleteFalse(String id);
}
