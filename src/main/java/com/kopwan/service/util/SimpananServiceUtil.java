package com.kopwan.service.util;

import com.kopwan.model.entity.Anggota;
import com.kopwan.model.entity.Simpanan;
import com.kopwan.model.request.SimpananRequest;
import com.kopwan.model.response.anggota.AnggotaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SimpananServiceUtil {

    public Simpanan convertToSimpanan(SimpananRequest request){
        Simpanan simpanan = new Simpanan();
        BeanUtils.copyProperties(request, simpanan);

        if (request.getAnggota() != null) {
            AnggotaResponse anggota = new AnggotaResponse();
            BeanUtils.copyProperties(request.getAnggota(), anggota);
            simpanan.setAnggota(anggota);
        }

        return simpanan;
    }
}
