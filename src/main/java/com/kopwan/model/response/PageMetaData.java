package com.kopwan.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageMetaData {
    private long pageSize = 0L;
    private long pageNumber = 0L;
    private long totalRecords = 0L;
}

