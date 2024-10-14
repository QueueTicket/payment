package com.qticket.payment.adapter.in.web.view.request;

import com.qticket.payment.application.port.in.CheckoutCommand;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.utils.IdempotencyGenerator;
import java.util.List;

public record CheckoutRequest(
    Long customerId,
    String concertId,
    List<String> seatIds,
    PaymentMethod paymentMethod,
    String couponId
) {

    public CheckoutCommand toCommand() {
        return new CheckoutCommand(
            customerId,
            concertId,
            seatIds,
            couponId,
            paymentMethod,
            IdempotencyGenerator.generate(this)
        );
    }

}
