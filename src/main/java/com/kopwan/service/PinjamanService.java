package com.kopwan.service;

import com.kopwan.dao.PinjamanRepository;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.ValidationException;
import com.kopwan.model.request.PinjamanRequest;
import com.kopwan.service.util.PinjamanServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PinjamanService {
    @Autowired
    private PinjamanRepository pinjamanRepository;
    @Autowired
    private PinjamanServiceUtil util;

    public Mono<Void> createPinjaman(PinjamanRequest request){
        return pinjamanRepository.findByAnggotaAndLunasFalseAndMarkForDeleteFalse(util.convertToAnggota(request))
                .flatMap(data -> Mono.error(new ValidationException(ErrorCode.ANGGOTA_STILL_HAS_PINJAMAN)))
                .switchIfEmpty(pinjamanRepository.save(util.convertToPinjaman(request)))
                .then();
    }
}
