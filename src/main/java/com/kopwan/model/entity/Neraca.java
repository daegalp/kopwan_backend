package com.kopwan.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kopwan.model.response.neraca.DebitKredit;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Document(collection = Neraca.COLLECTION_NAME)
public class Neraca extends BaseEntity {
    public static final String COLLECTION_NAME = "NERACA";
    private String name;
    private DebitKredit neracaSaldo;
    private DebitKredit mutasi;
    private DebitKredit neracaPercobaan;
    private DebitKredit penyesuaian;
    private DebitKredit labaRugi;
    private DebitKredit neraca;
    private int month;
    private int year;
}
