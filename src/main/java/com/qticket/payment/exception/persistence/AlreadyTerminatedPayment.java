package com.qticket.payment.exception.persistence;

import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;

public class AlreadyTerminatedPayment extends ApplicationException {

    private static final String MESSAGE_FORMAT = "이미 종료된 주문 입니다. [%s, %s]";

    public AlreadyTerminatedPayment(String orderId, PaymentStatus paymentStatus) {
        super(
            PaymentErrorCode.ALREADY_TERMINATED_ORDER,
            formattingErrorMessage(MESSAGE_FORMAT, orderId, paymentStatus)
        );
    }

}
