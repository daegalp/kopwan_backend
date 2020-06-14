package com.kopwan.service.util;

import com.kopwan.model.entity.Anggota;
import com.kopwan.model.request.AnggotaRequest;
import com.kopwan.model.response.anggota.AnggotaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AnggotaServiceUtil {

    public Anggota convertToAnggota(AnggotaRequest request){
        Anggota anggota = new Anggota();
        BeanUtils.copyProperties(request, anggota);
        return anggota;
    }

    public Anggota copyRequest(AnggotaRequest request, Anggota result){
        BeanUtils.copyProperties(request, result);
        return result;
    }

    public Anggota delete(Anggota anggota){
        anggota.setMarkForDelete(true);
        return anggota;
    }

    public AnggotaResponse convertToAnggotaResponse(Anggota anggota) {
        return AnggotaResponse.builder()
                .rw(anggota.getRw())
                .no(anggota.getNo())
                .name(anggota.getName())
                .build();
    }
}
