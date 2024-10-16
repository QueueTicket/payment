package com.qticket.payment.global.exception.response.validation;

public record InvalidDetail(
    String object,
    String field,
    String code,
    String RejectValue,
    Object rejectMessage
) {

    public static InvalidDetail of(
        String object,
        String field,
        String code,
        String RejectValue,
        Object rejectMessage
    ) {
        return new InvalidDetail(object, field, code, RejectValue, rejectMessage);
    }

}
