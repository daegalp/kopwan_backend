package com.kopwan.service.util;

import com.kopwan.model.entity.CicilanPinjaman;
import org.springframework.stereotype.Component;

@Component
public class CicilanPinjamanServiceUtil {

    public CicilanPinjaman delete(CicilanPinjaman cicilanPinjaman) {
        cicilanPinjaman.setMarkForDelete(true);
        return cicilanPinjaman;
    }
}
