package com.qticket.payment.global.exception.handler;

import com.qticket.payment.global.exception.ApplicationException;
import com.qticket.payment.global.exception.event.ThrowsExceptionEvent;
import com.qticket.payment.global.exception.response.ApplicationErrorResponse;
import com.qticket.payment.global.exception.response.MethodArgumentNotValidErrorResponse;
import com.qticket.payment.global.exception.response.code.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApplicationEventPublisher eventPublisher;

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ApplicationErrorResponse> unexpectedException(
        Exception exception,
        HttpServletRequest request
    ) {
        var errorCode = GlobalErrorCode.UNEXPECTED_ERROR;
        var errorResponse = ApplicationErrorResponse.handleGlobalError(request, errorCode);

        return handleResponse(exception, errorCode.status, errorResponse);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationErrorResponse> businessException(
        ApplicationException exception,
        HttpServletRequest request
    ) {
        var errorResponse = ApplicationErrorResponse.handleApplicationError(request, exception);

        return handleResponse(exception, exception.getStatus(), errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationErrorResponse> methodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        var errorCode = GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID;
        var errorResponse = MethodArgumentNotValidErrorResponse.of(request, exception.getFieldErrors());

        return handleResponse(exception, errorCode.status, errorResponse);
    }

    @ExceptionHandler({
        MissingPathVariableException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpMediaTypeNotAcceptableException.class,
        NoResourceFoundException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<ApplicationErrorResponse> invalidRequestException(
        Exception exception,
        HttpServletRequest request
    ) {
        var errorCode = GlobalErrorCode.valueOf(exception);
        var errorResponse = ApplicationErrorResponse.handleGlobalError(request, errorCode);

        return handleResponse(exception, errorCode.status, errorResponse);
    }

    private ResponseEntity<ApplicationErrorResponse> handleResponse(
        Exception exception,
        HttpStatus status,
        ApplicationErrorResponse errorResponse
    ) {
        eventPublisher.publishEvent(ThrowsExceptionEvent.of(exception, errorResponse));
        
        return ResponseEntity.status(status).body(errorResponse);
    }

}
