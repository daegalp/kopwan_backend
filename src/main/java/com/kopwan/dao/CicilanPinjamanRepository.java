package com.kopwan.dao;

import com.kopwan.model.entity.CicilanPinjaman;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CicilanPinjamanRepository
        extends ReactiveMongoRepository<CicilanPinjaman, String>, CicilanPinjamanRepositoryCustom {
}
