package com.qticket.payment.application.port.out.command.exception;

import com.qticket.payment.domain.payment.PaymentStatus;

public class InValidPaymentStatusException extends RuntimeException {

    private final String MESSAGE_FORMAT = "결제 상태가 올바르지 않습니다. [%s, %s] : %s";
    private String message;

    public InValidPaymentStatusException(String paymentKey, String orderId, PaymentStatus status) {
        this.message = MESSAGE_FORMAT.formatted(paymentKey, orderId, status);
    }

}
