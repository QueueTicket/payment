package com.qticket.payment.exception.application;

import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;
import lombok.Getter;

@Getter
public class InValidPaymentStatusException extends ApplicationException {

    private static final String MESSAGE_FORMAT = "결제 상태가 올바르지 않습니다. [paymentKey: %s, orderId: %s] : %s";
    private final PaymentStatus paymentStatus;

    public InValidPaymentStatusException(String paymentKey, String orderId, PaymentStatus status) {
        super(
            PaymentErrorCode.ALREADY_TERMINATED_ORDER,
            formattingErrorMessage(MESSAGE_FORMAT, paymentKey, orderId, status)
        );
        this.paymentStatus = status;
    }

}
