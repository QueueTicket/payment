package com.qticket.payment.adapter.in.web.api.request;

import com.qticket.payment.application.port.in.command.PaymentApproveCommand;

public record TossPaymentConfirmRequest(
    String paymentKey,
    String orderId,
    Long amount
) {

    public PaymentApproveCommand toCommand() {
        return new PaymentApproveCommand(paymentKey, orderId, amount);
    }

}
