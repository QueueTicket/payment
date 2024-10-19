package com.qticket.payment.global.exception;

import com.qticket.payment.global.exception.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }

    protected static String formattingErrorMessage(String messageFormat, Object... objects) {
        return messageFormat.formatted(objects);
    }

}
