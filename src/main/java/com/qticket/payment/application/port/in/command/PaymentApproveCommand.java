package com.qticket.payment.application.port.in.command;

public record PaymentApproveCommand(
    String paymentKey,
    String orderId,
    Long amount
) {

}
