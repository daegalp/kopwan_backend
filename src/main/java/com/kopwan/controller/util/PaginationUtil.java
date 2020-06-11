package com.kopwan.controller.util;

import com.kopwan.model.enums.ErrorCode;
import com.kopwan.model.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {

    public void validatePageRequest(int page, int size){
        validatePageRequest(page);
        validateSizeRequest(size);
    }

    private void validatePageRequest(int page) {
            if (page <= 0) {
                throw new ValidationException(ErrorCode.INVALID_PAGINATION_OF_PAGE);
            }
    }

    private void validateSizeRequest(int size) {
        if (size <= 0) {
            throw new ValidationException(ErrorCode.INVALID_PAGINATION_OF_SIZE);
        } else if (size > 100) {
            throw new ValidationException(ErrorCode.INVALID_PAGINATION_OF_MAX_SIZE);
        }
    }
}
