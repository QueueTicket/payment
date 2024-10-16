package com.qticket.payment.global.exception.event;

import com.qticket.payment.global.exception.response.ApplicationErrorResponse;
import lombok.Getter;

@Getter
public final class ThrowsExceptionEvent extends StackTraceExtractor {

    private final Exception exception;
    private final ApplicationErrorResponse response;

    private ThrowsExceptionEvent(
        Exception exception,
        ApplicationErrorResponse response
    ) {
        super(exception);
        this.exception = exception;
        this.response = response;
    }

    public static ThrowsExceptionEvent of(
        Exception exception,
        ApplicationErrorResponse response
    ) {
        return new ThrowsExceptionEvent(exception, response);
    }

}
