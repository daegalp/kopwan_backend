package com.kopwan.model.response.pinjaman;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kopwan.model.response.anggota.AnggotaResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PinjamanResponse {
    private String id;
    private AnggotaResponse anggota;
    private int nominal;
    private int month;
    private int year;
    private boolean lunas;
    private int actual;
    private int target;
}
