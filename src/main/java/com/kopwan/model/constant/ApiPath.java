package com.kopwan.model.constant;

public class ApiPath {
    // ANGGOTA PATH
    public static final String GET_ANGGOTA_BY_NAME = "/api/anggota/{name}";
    public static final String GET_ANGGOTA_BY_NO = "/api/anggota/{no}";
    public static final String ANGGOTA = "/api/anggota";

    // SIMPANAN PATH
    public static final String SIMPANAN = "/api/simpanan";
    public static final String GET_SIMPANAN_BY_ID = "/api/simpanan/{id}";

    // PINJAMAN PATH
    public static final String PINJAMAN = "/api/pinjaman";
    public static final String PINJAMAN_BY_ID = "/api/pinjaman/{id}";

    // CICILAN PINJAMAN PATH
    public static final String CICILAN = "/api/cicilan";
    public static final String CICILAN_BY_ID = "/api/cicilan/{id}";
}
