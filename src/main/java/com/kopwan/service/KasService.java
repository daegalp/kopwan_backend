package com.kopwan.service;

import com.kopwan.dao.KasRepository;
import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.entity.Kas;
import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.DataNotFoundException;
import com.kopwan.model.exception.ValidationException;
import com.kopwan.model.request.KasRequest;
import com.kopwan.model.request.param.CicilanPinjamanParam;
import com.kopwan.model.request.param.KasParamRequest;
import com.kopwan.model.request.param.PinjamanParamRequest;
import com.kopwan.model.request.param.SimpananParamRequest;
import com.kopwan.model.response.kas.TotalKasResponse;
import com.kopwan.service.util.KasServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class KasService {
    @Autowired
    private KasRepository kasRepository;
    @Autowired
    private KasServiceUtil util;
    @Autowired
    private SimpananService simpananService;
    @Autowired
    private PinjamanService pinjamanService;
    @Autowired
    private CicilanPinjamanService cicilanPinjamanService;

    Flux<Integer> rw = Flux.range(1, 9);
    Flux<String> type = Flux.just("WAJIB", "POKOK", "SUKARELA", "DUKA");

    public Mono<Void> createKas(KasRequest request){
        return kasRepository.save(util.convertToKas(request))
                .then();
    }

    public Mono<Kas> getById(String id) {
        return kasRepository.findByIdAndMarkForDeleteFalse(id);
    }

    public Mono<Kas> getByMonthAndYear(int month, int year) {
        return kasRepository.findByMonthAndYearAndMarkForDeleteFalse(month, year);
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

    public Mono<Page<Kas>> findAllByMonthAndYear(int month, int year, Pageable pageable) {
        return kasRepository.countAllByMonthAndYearAndMarkForDeleteFalse(month, year)
                .flatMap(count -> kasRepository.findAllByMonthAndYearAndMarkForDeleteFalse(month, year, pageable)
                        .collectList()
                        .map(data -> new PageImpl<>(data, pageable, count)));
    }

    public Mono<Void> deleteAllKasByMonthAndYear(int month, int year){
        Pageable pageable = PageRequest.of(0, 999);
        return kasRepository.findAllByMonthAndYearAndMarkForDeleteFalse(month, year, pageable)
                .flatMap(results -> deleteKas(results.getId()))
                .then();
    }

    public Mono<TotalKasResponse> getTotalKasByMonthAndYear(int month, int year) {
        Pageable pageable = PageRequest.of(0, 999);
        return kasRepository.findAllByMonthAndYearAndMarkForDeleteFalse(month, year, pageable)
                .collectList()
                .map(this::countTotalKas);
    }

    private TotalKasResponse countTotalKas(List<Kas> kasList) {
        TotalKasResponse total = new TotalKasResponse();
        for (Kas kas : kasList) {
            if(kas.getAlurKas().equalsIgnoreCase("MASUK"))
                total.setKasMasuk(total.getKasMasuk() + kas.getNominal());
            else if(kas.getAlurKas().equalsIgnoreCase("KELUAR"))
                total.setKasKeluar(total.getKasKeluar() + kas.getNominal());
        }
        return total;
    }

    public Mono<Void> generateBukuKas(KasParamRequest request) {
        return getByMonthAndYear(request.getMonth(), request.getYear())
                .flatMap(data -> Mono.error(new ValidationException(ErrorCode.INVALID_GENERATE)))
                .switchIfEmpty(generate(request))
                .then();
    }

    public Mono<Void> generate(KasParamRequest request) {
        return generateSimpanan(request)
                .then(generatePinjaman(request))
                .then(generateAngsuranPokok(request))
                .then(generateAngsuranJasa(request));
    }

    private Mono<Void> generateSimpanan(KasParamRequest request) {
        return rw.flatMap(rw -> saveSimpananByType(request, rw))
                .then();
    }

    private Mono<Void> saveSimpananByType(KasParamRequest request, int rw) {
        return type.flatMap(type -> saveAllSimpanan(convertToSimpananParam(request, rw, type)))
                .then();
    }

    private Mono<Void> saveAllSimpanan(SimpananParamRequest request) {
        return simpananService.filterSimpanan(request)
                .map(Slice::getContent)
                .map(this::countTotalSimpanan)
                .filter(total -> total != 0)
                .flatMap(total -> createKas(createKasRequest("Simpanan RW " + request.getRw(),
                        request.getType(), "MASUK", total,
                        request.getMonth(), request.getYear())))
                .then();
    }

    private int countTotalSimpanan(List<Simpanan> simpanans) {
        int total = 0;

        for (Simpanan simpanan : simpanans)
            total += simpanan.getNominal();

        return total;
    }

    private Mono<Void> generatePinjaman(KasParamRequest request) {
        return rw.flatMap(rw -> saveAllPinjaman(convertToPinjamanParam(request, rw)))
                .then();
    }

    private Mono<Void> saveAllPinjaman(PinjamanParamRequest request) {
        return pinjamanService.filterPinjaman(request)
                .map(Slice::getContent)
                .map(this::countTotalPinjaman)
                .filter(total -> total != 0)
                .flatMap(total -> createKas(createKasRequest("Pemberian Pinjaman kpd RW " + request.getRw(),
                        "PEMBERIAN PINJAMAN", "KELUAR", total,
                        request.getMonth(), request.getYear())))
                .then();
    }

    private int countTotalPinjaman(List<Pinjaman> pinjamans) {
        int total = 0;

        for (Pinjaman pinjaman : pinjamans)
            total += pinjaman.getNominal();

        return total;
    }

    private Mono<Void> generateAngsuranPokok(KasParamRequest request) {
        return rw.flatMap(rw -> saveAllAngsuranPokok(convertToCicilanParam(request, rw)))
                .then();
    }

    private Mono<Void> saveAllAngsuranPokok(CicilanPinjamanParam request) {
        return cicilanPinjamanService.filterCicilan(request)
                .map(Slice::getContent)
                .map(this::countTotalAngsuranPokok)
                .filter(total -> total != 0)
                .flatMap(total -> createKas(createKasRequest("Terima angsuran pokok RW " + request.getRw(),
                        "ANGSURAN POKOK", "MASUK", total,
                        request.getMonth(), request.getYear())))
                .then();
    }

    private int countTotalAngsuranPokok(List<CicilanPinjaman> cicilanPinjamanList) {
        int total = 0;

        for (CicilanPinjaman cicilanPinjaman : cicilanPinjamanList)
            total += cicilanPinjaman.getPokok();

        return total;
    }

    private Mono<Void> generateAngsuranJasa(KasParamRequest request) {
        return rw.flatMap(rw -> saveAllAngsuranJasa(convertToCicilanParam(request, rw)))
                .then();
    }

    private Mono<Void> saveAllAngsuranJasa(CicilanPinjamanParam request) {
        return cicilanPinjamanService.filterCicilan(request)
                .map(Slice::getContent)
                .map(this::countTotalAngsuranJasa)
                .filter(total -> total != 0)
                .flatMap(total -> createKas(createKasRequest("Terima angsuran jasa RW " + request.getRw(),
                        "ANGSURAN JASA", "MASUK", total,
                        request.getMonth(), request.getYear())))
                .then();
    }

    private int countTotalAngsuranJasa(List<CicilanPinjaman> cicilanPinjamanList) {
        int total = 0;

        for (CicilanPinjaman cicilanPinjaman : cicilanPinjamanList)
            total += cicilanPinjaman.getJasa();

        return total;
    }

    private SimpananParamRequest convertToSimpananParam(KasParamRequest request, int rw, String type) {
        return SimpananParamRequest.builder()
                .page(1)
                .size(999)
                .rw(rw)
                .type(type)
                .month(request.getMonth())
                .year(request.getYear())
                .build();
    }

    private PinjamanParamRequest convertToPinjamanParam(KasParamRequest request, int rw) {
        return PinjamanParamRequest.builder()
                .page(1)
                .size(999)
                .rw(rw)
                .month(request.getMonth())
                .year(request.getYear())
                .build();
    }

    private CicilanPinjamanParam convertToCicilanParam(KasParamRequest request, int rw) {
        return CicilanPinjamanParam.builder()
                .page(1)
                .size(999)
                .rw(rw)
                .month(request.getMonth())
                .year(request.getYear())
                .build();
    }

    private KasRequest createKasRequest(String name, String type, String alurKas,
                                        int nominal, int month, int year) {
        return KasRequest.builder()
                .name(name)
                .type(type)
                .alurKas(alurKas)
                .nominal(nominal)
                .month(month)
                .year(year)
                .build();
    }
}
