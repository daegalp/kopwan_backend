package com.kopwan.service.util;

import com.kopwan.model.entity.Anggota;
import com.kopwan.model.entity.Kas;
import com.kopwan.model.request.KasRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class KasServiceUtil {

    public Kas convertToKas(KasRequest request) {
        Kas kas = new Kas();
        BeanUtils.copyProperties(request, kas);
        return kas;
    }

    public Kas copyRequest(KasRequest request, Kas result){
        BeanUtils.copyProperties(request, result);
        return result;
    }

    public Kas delete(Kas kas){
        kas.setMarkForDelete(true);
        return kas;
    }
}
