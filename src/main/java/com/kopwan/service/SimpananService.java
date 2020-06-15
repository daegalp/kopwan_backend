package com.kopwan.service;

import com.kopwan.dao.SimpananRepository;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.request.SimpananRequest;
import com.kopwan.model.request.param.SimpananParamRequest;
import com.kopwan.service.util.AnggotaServiceUtil;
import com.kopwan.service.util.SimpananServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SimpananService {
    @Autowired
    private SimpananRepository simpananRepository;
    @Autowired
    private SimpananServiceUtil util;
    @Autowired
    private AnggotaService anggotaService;

    public Mono<Void> createSimpanan(SimpananRequest request){
        return anggotaService.findAnggotaResponseByNo(request.getNo())
                .flatMap(result -> simpananRepository.save(util.convertToSimpanan(request, result)))
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

    public Mono<Void> deleteSimpanan(String id) {
        return simpananRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.SIMPANAN_NOT_FOUND)))
                .flatMap(result -> simpananRepository.save(util.delete(result)))
                .then();
    }

    public Mono<Page<Simpanan>> filterSimpanan(SimpananParamRequest request){
        return simpananRepository.filterSimpanan(request);
    }
}
