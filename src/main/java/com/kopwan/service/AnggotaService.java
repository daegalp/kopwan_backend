package com.kopwan.service;

import com.kopwan.dao.AnggotaRepository;
import com.kopwan.model.entity.Anggota;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.request.AnggotaRequest;
import com.kopwan.service.util.AnggotaServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AnggotaService {

    @Autowired
    private AnggotaRepository anggotaRepository;
    @Autowired
    private AnggotaServiceUtil anggotaServiceUtil;

    public Mono<Anggota> findAnggotaByName(String name) {
        return anggotaRepository.findByNameAndMarkForDeleteFalse(name)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.NAME_NOT_FOUND)));
    }

    public Mono<Void> createAnggota(AnggotaRequest request){
        return anggotaRepository.findByNoAndMarkForDeleteFalse(request.getNo())
                .flatMap(data -> Mono.error(new DataNotFoundException(ErrorCode.NO_ANGGOTA_NOT_UNIQUE)))
                .switchIfEmpty(anggotaRepository.save(anggotaServiceUtil.convertToAnggota(request)))
                .then();
    }

    public Mono<Page<Anggota>> findAllAnggota(Pageable pageable){
        return anggotaRepository.countByMarkForDeleteFalse()
                .flatMap(count -> this.anggotaRepository.findAllByMarkForDeleteFalseOrderByRw(pageable)
                        .collectList()
                        .map(data -> new PageImpl<>(data, pageable, count)));
    }

    public Mono<Anggota> updateAnggota(AnggotaRequest request){
        return anggotaRepository.findByNoAndMarkForDeleteFalse(request.getNo())
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.NO_ANGGOTA_NOT_FOUND)))
                .flatMap(result -> anggotaRepository.save(anggotaServiceUtil.copyRequest(request, result)));
    }

    public Mono<Anggota> deleteAnggota(String no){
        return anggotaRepository.findByNoAndMarkForDeleteFalse(no)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.NO_ANGGOTA_NOT_FOUND)))
                .flatMap(result -> anggotaRepository.save(anggotaServiceUtil.delete(result)));
    }
}
