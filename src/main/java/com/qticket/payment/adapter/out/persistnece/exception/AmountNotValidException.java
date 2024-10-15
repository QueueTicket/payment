package com.qticket.payment.adapter.out.persistnece.exception;

import java.math.BigDecimal;

public class AmountNotValidException extends RuntimeException {

    private final String messageFormat = "주문 번호[%s]의 결제 금액이 올바르지 않습니다. %s != %s";
    private String message;

    public AmountNotValidException(String orderId, Long amount, BigDecimal totalAmount) {
        this.message = messageFormat.formatted(orderId, amount, totalAmount);
    }

}
