package com.kopwan.service.util;

import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.request.SimpananRequest;
import com.kopwan.model.response.anggota.AnggotaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SimpananServiceUtil {

    public Simpanan convertToSimpanan(SimpananRequest request, AnggotaResponse anggota){
        Simpanan simpanan = new Simpanan();
        BeanUtils.copyProperties(request, simpanan);
        simpanan.setAnggota(anggota);

        return simpanan;
    }

    public Simpanan copyRequest(SimpananRequest request, Simpanan result){
        BeanUtils.copyProperties(request, result);
        return result;
    }

    public Simpanan delete(Simpanan simpanan){
        simpanan.setMarkForDelete(true);
        return simpanan;
    }
}
