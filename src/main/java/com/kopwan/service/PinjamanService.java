package com.kopwan.service;

import com.kopwan.dao.PinjamanRepository;
import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.exception.ValidationException;
import com.kopwan.model.request.CicilanRequest;
import com.kopwan.model.request.PinjamanRequest;
import com.kopwan.model.request.param.PinjamanParamRequest;
import com.kopwan.service.util.AnggotaServiceUtil;
import com.kopwan.service.util.PinjamanServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PinjamanService {
    @Autowired
    private PinjamanRepository pinjamanRepository;
    @Autowired
    private PinjamanServiceUtil util;
    @Autowired
    private AnggotaService anggotaService;
    @Autowired
    private CicilanPinjamanService cicilanPinjamanService;

    public Mono<Void> createPinjaman(PinjamanRequest request){
        return anggotaService.findByAllField(request.getAnggota())
                .flatMap(data -> pinjamanRepository.findByAnggotaAndLunasFalseAndMarkForDeleteFalse(util.convertToAnggota(request)))
                .flatMap(data -> Mono.error(new ValidationException(ErrorCode.ANGGOTA_STILL_HAS_PINJAMAN)))
                .switchIfEmpty(pinjamanRepository.save(util.convertToPinjaman(request)))
                .then();
    }

    public Mono<Page<Pinjaman>> filterPinjaman(PinjamanParamRequest request){
        return pinjamanRepository.filterPinjaman(request);
    }

    public Mono<Pinjaman> updatePinjaman(String id, PinjamanRequest request) {
        return pinjamanRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.PINJAMAN_NOT_FOUND)))
                .flatMap(result -> pinjamanRepository.save(util.copyRequest(request, result)));
    }

    public Mono<Void> deletePinjaman(String id) {
        return pinjamanRepository.findByIdAndMarkForDeleteFalse(id)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.PINJAMAN_NOT_FOUND)))
                .flatMap(result -> pinjamanRepository.save(util.delete(result)))
                .then();
    }

    public Mono<Pinjaman> findByNoAnggota(String no) {
        return anggotaService.findAnggotaResponseByNo(no)
                .flatMap(anggota -> pinjamanRepository.findByAnggotaAndLunasFalseAndMarkForDeleteFalse(anggota))
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.ANGGOTA_DOESNT_HAS_PINJAMAN)));
    }

    public Mono<Void> updateActual(String no) {
        return this.findByNoAnggota(no)
                .flatMap(pinjaman -> pinjamanRepository.save(util.updateActual(pinjaman)))
                .then();
    }

    public Mono<Void> updateLunas(CicilanRequest request) {
        return cicilanPinjamanService.lunasCicilan(request)
                .flatMap(result -> findByNoAnggota(request.getNo()))
                .flatMap(result -> pinjamanRepository.save(util.lunas(result)))
                .then();
    }
}
