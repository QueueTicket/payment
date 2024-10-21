package com.qticket.payment.exception;

import com.qticket.payment.global.exception.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentErrorCode implements ErrorCode {
    INVALID_PAYMENT_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 금액과 주문 금액이 일치하지 않습니다."),
    INVALID_APPROVAL_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 상태가 올바르지 않습니다."),
    ALREADY_TERMINATED_ORDER(HttpStatus.BAD_REQUEST, "이미 종료된 주문 입니다."),
    ALREADY_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "결제 상태가 올바르지 않습니다."),
    MISSING_APPROVE_DETAILS(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 상세 정보가 누락되었습니다."),
    PAYMENT_APPROVED_TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 지연 오류가 발생하였습니다.");;

    private final HttpStatus status;
    private final String message;

    PaymentErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
