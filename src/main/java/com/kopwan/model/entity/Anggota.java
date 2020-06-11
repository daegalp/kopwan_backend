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
@Document(collection = Anggota.COLLECTION_NAME)
public class Anggota extends BaseEntity {
    public static final String COLLECTION_NAME = "ANGGOTA";
    private String no;
    private String name;
    private int rw;
}
