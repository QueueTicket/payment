package com.qticket.payment.adapter.in.web.api.request;

import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;

public record TossPaymentConfirmRequest(
    String paymentKey,
    String orderId,
    Long amount
) {

    public PaymentConfirmCommand toCommand() {
        return new PaymentConfirmCommand(paymentKey, orderId, amount);
    }

}
