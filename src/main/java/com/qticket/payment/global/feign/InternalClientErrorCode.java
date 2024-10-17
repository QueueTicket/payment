package com.qticket.payment.global.feign;

import com.qticket.payment.global.exception.response.code.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalClientErrorCode implements ErrorCode {

    private HttpStatus status;
    private String message;
    private String code;

    public static InternalClientErrorCode of(HttpStatus status, String message, String code) {
        return new InternalClientErrorCode(status, message, code);
    }

}
