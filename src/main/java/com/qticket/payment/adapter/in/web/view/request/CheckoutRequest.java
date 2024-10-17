package com.qticket.payment.adapter.in.web.view.request;

import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.global.utils.IdempotencyGenerator;
import java.util.List;

public record CheckoutRequest(
    Long customerId,
    String couponId,
    String concertId,
    List<String> seatIds,
    PaymentMethod paymentMethod
) {

    public CheckoutCommand toCommand() {
        return new CheckoutCommand(
            customerId,
            couponId,
            concertId,
            seatIds,
            paymentMethod,
            IdempotencyGenerator.generate(this)
        );
    }

}
