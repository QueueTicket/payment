package com.qticket.payment.global.exception.response.code;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode implements ErrorCode {
    MISSING_PATH_VARIABLE(BAD_REQUEST, "경로에 필요한 변수가 없습니다. 요청 URI를 확인해 주세요."),
    MISSING_SERVLET_REQUEST_PARAMETER(BAD_REQUEST, "필수 요청 값이 누락되었습니다. 요청 파라미터를 확인해 주세요."),
    METHOD_ARGUMENT_TYPE_MISMATCH(BAD_REQUEST, "요청 값의 자료형이 올바르지 않습니다. 자료형을 다시 확인해 주세요."),
    METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, "유효하지 않은 요청 값이 포함되었습니다. 필수 항목이 빠졌거나 자료형이 잘못되었습니다. 입력 데이터를 다시 확인해 주세요."),
    HTTP_MESSAGE_NOT_READABLE(BAD_REQUEST, "요청 본문을 읽을 수 없습니다. 자료형이나 데이터를 확인해 주세요."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(BAD_REQUEST, "요청하신 응답 형식을 처리할 수 없습니다. Accept 헤더 값을 확인해 주세요."),
    NO_RESOURCE_FOUND(NOT_FOUND, "해당 경로에 대한 리소스를 찾을 수 없습니다. URL이나 API 경로를 확인해 주세요."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드입니다. 요청 메소드를 확인해 주세요."),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type입니다. Content-Type 헤더를 확인해 주세요."),
    UNEXPECTED_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해 주세요. 문제가 지속되면 관리자에게 문의해 주세요."),
    ;

    public final HttpStatus status;
    public final String message;

    GlobalErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static GlobalErrorCode valueOf(Exception exception) {
        String errorCodeName = convertExceptionClassNameToErrorCodeName(exception);
        try {
            return valueOf(errorCodeName);
        } catch (IllegalArgumentException e) {
            return GlobalErrorCode.UNEXPECTED_ERROR;
        }
    }

    private static String convertExceptionClassNameToErrorCodeName(Exception exception) {
        String className = extractExceptionClassName(exception);
        return convertToErrorCodeName(className);
    }

    private static String extractExceptionClassName(Exception exception) {
        return exception.getClass().getSimpleName();
    }

    private static String convertToErrorCodeName(String className) {
        String exceptKeyword = "_EXCEPTION";
        return convertToConstants(className).replace(exceptKeyword, "");
    }

    private static String convertToConstants(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        return value.replaceAll(regex, replacement)
            .toUpperCase();
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
