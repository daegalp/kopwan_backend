package com.kopwan.dao;

import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.response.anggota.AnggotaResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PinjamanRepository extends ReactiveMongoRepository<Pinjaman, String>, PinjamanRepositoryCustom {
    Mono<Pinjaman> findByIdAndMarkForDeleteFalse(String id);
    Mono<Pinjaman> findByAnggotaAndLunasFalseAndMarkForDeleteFalse(AnggotaResponse anggota);
}
