package com.kopwan.service.util;

import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.request.PinjamanRequest;
import com.kopwan.model.response.anggota.AnggotaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PinjamanServiceUtil {

    public AnggotaResponse convertToAnggota(PinjamanRequest request){
        return AnggotaResponse.builder()
                .no(request.getAnggota().getNo())
                .name(request.getAnggota().getName())
                .rw(request.getAnggota().getRw())
                .build();
    }

    public Pinjaman convertToPinjaman(PinjamanRequest request) {
        Pinjaman pinjaman = new Pinjaman();
        BeanUtils.copyProperties(request, pinjaman);
        return pinjaman;
    }

    public Pinjaman delete(Pinjaman pinjaman) {
        pinjaman.setMarkForDelete(true);
        return pinjaman;
    }

    public Pinjaman copyRequest(PinjamanRequest request, Pinjaman result){
        BeanUtils.copyProperties(request, result);
        return result;
    }

    public Pinjaman updateActual(Pinjaman pinjaman) {
        pinjaman.setActual(pinjaman.getActual() + 1);
        return pinjaman;
    }

    public Pinjaman lunas(Pinjaman pinjaman) {
        pinjaman.setActual(pinjaman.getActual() + 1);
        pinjaman.setLunas(true);
        return pinjaman;
    }
}
