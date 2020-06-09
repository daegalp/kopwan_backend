package com.kopwan.service;

import com.kopwan.dao.AnggotaRepository;
import com.kopwan.model.Anggota;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AnggotaService {

    @Autowired
    private AnggotaRepository anggotaRepository;

    public Mono<Anggota> findAnggotaByName(String name) {
        return anggotaRepository.findByNameAndMarkForDeleteFalse(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.NAME_NOT_FOUND)));
    }

    public Mono<List<Anggota>> findAllAnggota(){
        return anggotaRepository.findAllByMarkForDeleteFalse()
                .collectList();
    }
}
