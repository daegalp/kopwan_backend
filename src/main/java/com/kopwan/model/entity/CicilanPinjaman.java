package com.kopwan.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kopwan.model.response.anggota.AnggotaResponse;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Document(collection = CicilanPinjaman.COLLECTION_NAME)
public class CicilanPinjaman extends BaseEntity {
    public static final String COLLECTION_NAME = "CICILAN_PINJAMAN";
    private AnggotaResponse anggota;
    private int pokok;
    private int jasa;
    private int month;
    private int year;
}
