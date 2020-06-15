package com.kopwan.service;

import com.kopwan.dao.KasRepository;
import com.kopwan.model.entity.Kas;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.request.KasRequest;
import com.kopwan.service.util.KasServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KasService {
    @Autowired
    private KasRepository kasRepository;
    @Autowired
    private KasServiceUtil util;

    public Mono<Void> createKas(KasRequest request){
        return kasRepository.save(util.convertToKas(request))
                .then();
    }

    public Mono<Kas> getById(String id) {
        return kasRepository.findByIdAndMarkForDeleteFalse(id);
    }

    public Mono<Kas> updateKas(String id, KasRequest request){
        return kasRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.KAS_NOT_FOUND)))
                .flatMap(result -> kasRepository.save(util.copyRequest(request, result)));
    }

    public Mono<Kas> deleteKas(String id){
        return kasRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.KAS_NOT_FOUND)))
                .flatMap(result -> kasRepository.save(util.delete(result)));
    }
}
