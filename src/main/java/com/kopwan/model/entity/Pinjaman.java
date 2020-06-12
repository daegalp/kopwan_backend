package com.kopwan.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kopwan.model.response.anggota.AnggotaResponse;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = Pinjaman.COLLECTION_NAME)
public class Pinjaman extends BaseEntity {
    public static final String COLLECTION_NAME = "PINJAMAN";
    private AnggotaResponse anggota;
    private int nominal;
    private String month;
    private int year;
    private boolean lunas;
    private int actual;
    private int target;
}
