package com.kopwan.service;

import com.kopwan.dao.SimpananRepository;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.request.SimpananRequest;
import com.kopwan.service.util.SimpananServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SimpananService {
    @Autowired
    private SimpananRepository simpananRepository;
    @Autowired
    private SimpananServiceUtil util;

    public Mono<Void> createSimpanan(SimpananRequest request){
        return simpananRepository.save(util.convertToSimpanan(request))
                .then();
    }

    public Mono<Simpanan> getDetailSimpanan(String id){
        return simpananRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.SIMPANAN_NOT_FOUND)));
    }

    public Mono<Simpanan> updateSimpanan(String id, SimpananRequest request){
        return simpananRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.SIMPANAN_NOT_FOUND)))
                .flatMap(result -> simpananRepository.save(util.copyRequest(request, result)));
    }
}
