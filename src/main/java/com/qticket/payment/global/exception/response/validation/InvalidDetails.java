package com.qticket.payment.global.exception.response.validation;

import java.util.List;
import org.springframework.validation.FieldError;

public record InvalidDetails(
    List<InvalidDetail> elements
) {

    public static InvalidDetails from(List<FieldError> fieldErrors) {
        return new InvalidDetails(
            fieldErrors.stream()
                .map(fieldError -> InvalidDetail.of(
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue()
                )).toList()
        );
    }

}
