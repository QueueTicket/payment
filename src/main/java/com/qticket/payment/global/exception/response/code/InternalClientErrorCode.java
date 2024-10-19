package com.qticket.payment.global.exception.response.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalClientErrorCode implements ErrorCode {

    private HttpStatus status;
    private String code;
    private String message;

    public static InternalClientErrorCode of(HttpStatus status, String message, String code) {
        return new InternalClientErrorCode(status, message, code);
    }

}
