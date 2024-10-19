package com.qticket.payment.global.exception;

import com.qticket.payment.global.exception.response.code.InternalClientErrorCode;
import lombok.Getter;

@Getter
public class InternalClientException extends ApplicationException {

    private final InternalClientErrorCode errorCode;

    public InternalClientException(InternalClientErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
