package com.qticket.payment.application.port.in;

import com.qticket.payment.domain.payment.PaymentMethod;
import java.util.List;

public record CheckoutCommand(
    Long customerId,
    String concertId,
    List<String> seatIds,
    String couponId,
    PaymentMethod paymentMethod,
    String idempotencyKey
) {

}
