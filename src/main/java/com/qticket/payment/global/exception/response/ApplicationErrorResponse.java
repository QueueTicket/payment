package com.qticket.payment.global.exception.response;

import static lombok.AccessLevel.PROTECTED;

import com.qticket.payment.global.annotations.DateTimeFormat;
import com.qticket.payment.global.exception.ApplicationException;
import com.qticket.payment.global.exception.response.code.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PROTECTED)
public sealed class ApplicationErrorResponse permits MethodArgumentNotValidErrorResponse {

    private String method;
    private String path;
    private String code;
    private String message;
    @DateTimeFormat
    private LocalDateTime timestamp;

    public static ApplicationErrorResponse handleGlobalError(
        HttpServletRequest request,
        GlobalErrorCode errorCode
    ) {
        return new ApplicationErrorResponse(
            request.getMethod(),
            request.getRequestURI(),
            errorCode.name(),
            errorCode.getMessage(),
            LocalDateTime.now()
        );
    }

    public static ApplicationErrorResponse handleApplicationError(
        HttpServletRequest request,
        ApplicationException exception
    ) {
        return new ApplicationErrorResponse(
            request.getMethod(),
            request.getRequestURI(),
            exception.getCode(),
            exception.getMessage(),
            LocalDateTime.now()
        );
    }

}
