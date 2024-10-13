package com.qticket.payment.adapter.in.web.api.request;

public record TossPaymentConfirmRequest(
    String paymentKey,
    String orderId,
    String amount
) {

}
