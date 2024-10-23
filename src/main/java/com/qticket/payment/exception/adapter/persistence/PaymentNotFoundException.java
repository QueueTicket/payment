package com.qticket.payment.exception.adapter.persistence;

import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;

public class PaymentNotFoundException extends ApplicationException {

    private static final String MESSAGE_FORMAT = "요청에 해당하는 결제 정보를 찾을 수 없습니다. [%s]";

    public PaymentNotFoundException(PaymentErrorCode paymentErrorCode, String orderId) {
        super(paymentErrorCode, formattingErrorMessage(MESSAGE_FORMAT, orderId));
    }

}
