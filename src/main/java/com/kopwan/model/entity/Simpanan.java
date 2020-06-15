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
@Document(collection = Simpanan.COLLECTION_NAME)
public class Simpanan extends BaseEntity{
    public static final String COLLECTION_NAME = "SIMPANAN";
    private AnggotaResponse anggota;
    private String type;
    private int nominal;
    private int month;
    private int year;
}
