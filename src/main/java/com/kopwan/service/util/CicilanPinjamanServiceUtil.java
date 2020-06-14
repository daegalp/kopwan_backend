package com.kopwan.service.util;

import com.kopwan.model.entity.CicilanPinjaman;
import com.kopwan.model.entity.Pinjaman;
import com.kopwan.model.request.CicilanRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CicilanPinjamanServiceUtil {

    public CicilanPinjaman convertToCicilan(CicilanRequest request, Pinjaman pinjaman) {
        return CicilanPinjaman.builder()
                .anggota(pinjaman.getAnggota())
                .pokok(getValuePokok(pinjaman.getNominal(), pinjaman.getTarget()))
                .jasa(getValueJasa(pinjaman.getNominal()))
                .month(request.getMonth())
                .year(request.getYear())
                .build();
    }

    public CicilanPinjaman delete(CicilanPinjaman cicilanPinjaman) {
        cicilanPinjaman.setMarkForDelete(true);
        return cicilanPinjaman;
    }

    public CicilanPinjaman copyRequest(CicilanRequest request, CicilanPinjaman result){
        BeanUtils.copyProperties(request, result);
        return result;
    }

    private int getValuePokok(int nominal, int target) {
        return nominal / target;
    }

    private int getValueJasa(int nominal) {
        return nominal * 5 / 100;
    }
}
