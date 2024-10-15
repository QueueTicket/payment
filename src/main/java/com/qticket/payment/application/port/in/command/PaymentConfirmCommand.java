package com.qticket.payment.application.port.in.command;

public record PaymentConfirmCommand(
    String paymentKey,
    String orderId,
    Long amount
) {

}
