package com.qticket.payment.exception.application;

import com.qticket.payment.domain.payment.PaymentStatus;

public class MissingApproveCompleteDetailsException extends RuntimeException {

    private final String MESSAGE_FORMAT = "결제 상세 정보가 누락되었습니다.[%s, %s] : %s";
    private String message;

    public MissingApproveCompleteDetailsException(String paymentKey, String orderId, PaymentStatus status) {
        this.message = MESSAGE_FORMAT.formatted(paymentKey, orderId, status);
    }

}
