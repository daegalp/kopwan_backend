package com.kopwan.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "parentBuilder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity implements Serializable, Persistable<String> {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Builder.Default
    private Date createdDate = new Date();

    private String createdBy;

    @Builder.Default
    private Date updatedDate = new Date();

    private String updatedBy;

    private String storeId;

    @Builder.Default
    private boolean markForDelete = false;

    @Override
    public boolean isNew() {
        return false;
    }
}