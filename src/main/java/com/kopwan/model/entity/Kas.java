package com.kopwan.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Document(collection = Kas.COLLECTION_NAME)
public class Kas extends BaseEntity{
    public static final String COLLECTION_NAME = "KAS";
    private String name;
    private String type;
    private String alurKas;
    private int nominal;
    private int month;
    private int year;
}
