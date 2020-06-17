package com.kopwan.service;

import com.kopwan.dao.AnggotaRepository;
import com.kopwan.model.entity.Anggota;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.request.AnggotaRequest;
import com.kopwan.model.response.anggota.AnggotaResponse;
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

    public Mono<Anggota> findByNoAnggota(String no) {
        return anggotaRepository.findByNoAndMarkForDeleteFalse(no)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.NO_ANGGOTA_NOT_FOUND)));
    }

    public Mono<AnggotaResponse> findAnggotaResponseByNo(String no) {
        return anggotaRepository.findByNoAndMarkForDeleteFalse(no)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.NO_ANGGOTA_NOT_FOUND)))
                .map(anggota -> anggotaServiceUtil.convertToAnggotaResponse(anggota));
    }

    public Mono<Anggota> findByAllField(AnggotaResponse anggota) {
        return anggotaRepository.findByNoAndNameAndRwAndMarkForDeleteFalse(anggota.getNo(),
                    anggota.getName(), anggota.getRw())
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorCode.ANGGOTA_NOT_FOUND)));
    }

    public Mono<Page<Anggota>> filterAnggota(String name, Pageable pageable) {
        return anggotaRepository.countByNameLikeAndMarkForDeleteFalse(name)
                .flatMap(count -> this.anggotaRepository.findAllByNameLikeIgnoreCaseAndMarkForDeleteFalse(name, pageable)
                        .collectList()
                        .map(data -> new PageImpl<>(data, pageable, count)));
    }
}
