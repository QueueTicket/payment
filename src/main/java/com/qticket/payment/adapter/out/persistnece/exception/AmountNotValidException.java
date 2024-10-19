package com.qticket.payment.adapter.out.persistnece.exception;

import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;
import java.math.BigDecimal;

// TODO move to exception packages
public class AmountNotValidException extends ApplicationException {

    private final String messageFormat = "주문 번호[%s]의 결제 금액이 올바르지 않습니다. %s != %s";
    private String message;

    public AmountNotValidException(String orderId, Long amount, BigDecimal totalAmount) {
        super(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        this.message = messageFormat.formatted(orderId, amount, totalAmount);
    }

}
