package com.qticket.payment.application.port.in.command;

import com.qticket.payment.domain.payment.PaymentMethod;
import java.util.List;

public record CheckoutCommand(
    Long customerId,
    String couponId,
    String concertId,
    List<String> seatIds,
    PaymentMethod paymentMethod,
    String idempotencyKey
) {

}
