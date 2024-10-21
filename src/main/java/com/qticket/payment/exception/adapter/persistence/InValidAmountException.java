package com.qticket.payment.exception.adapter.persistence;

import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;
import java.math.BigDecimal;

public class InValidAmountException extends ApplicationException {

    private static final String MESSAGE_FORMAT = "주문 번호[%s]의 결제 금액이 올바르지 않습니다. [%s != %s]";

    public InValidAmountException(String orderId, Long amount, BigDecimal totalAmount) {
        super(
            PaymentErrorCode.INVALID_PAYMENT_AMOUNT,
            formattingErrorMessage(MESSAGE_FORMAT, orderId, amount, totalAmount)
        );
    }

}
