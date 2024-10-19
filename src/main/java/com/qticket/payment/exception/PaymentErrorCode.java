package com.qticket.payment.exception;

import com.qticket.payment.global.exception.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// TODO 상세 오류 메세지 출력을 위한 파라미터 수신 처리 추가
@Getter
public enum PaymentErrorCode implements ErrorCode {
    INVALID_PAYMENT_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 금액과 주문 금액이 일치하지 않습니다.");

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
