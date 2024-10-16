package com.qticket.payment.global.exception.response;

import com.qticket.payment.global.exception.response.code.GlobalErrorCode;
import com.qticket.payment.global.exception.response.validation.InvalidDetails;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public final class MethodArgumentNotValidErrorResponse extends ApplicationErrorResponse {

    private final InvalidDetails invalidDetails;

    private MethodArgumentNotValidErrorResponse(
        HttpServletRequest request,
        GlobalErrorCode errorCode,
        InvalidDetails invalidDetails
    ) {
        super(
            request.getMethod(),
            request.getRequestURI(),
            errorCode.name(),
            errorCode.getMessage(),
            LocalDateTime.now()
        );
        this.invalidDetails = invalidDetails;
    }

    public static MethodArgumentNotValidErrorResponse of(
        HttpServletRequest request,
        GlobalErrorCode globalErrorCode,
        List<FieldError> fieldErrors
    ) {
        return new MethodArgumentNotValidErrorResponse(
            request,
            globalErrorCode,
            InvalidDetails.from(fieldErrors)
        );
    }

}
