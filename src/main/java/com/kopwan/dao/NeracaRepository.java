package com.kopwan.dao;

import com.kopwan.model.entity.Neraca;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeracaRepository extends ReactiveMongoRepository<Neraca, String> {
}
