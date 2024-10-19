package com.qticket.payment.domain.checkout;

import com.qticket.payment.domain.payment.PaymentEvent;
import java.math.BigDecimal;

public record CheckoutResult(
    String orderId,
    String orderName,
    BigDecimal amount,
    BigDecimal discountAmount
) {

    public static CheckoutResult of(PaymentEvent paymentEvent) {
        return new CheckoutResult(
            paymentEvent.orderId(),
            paymentEvent.orderName(),
            paymentEvent.totalAmount(),
            paymentEvent.benefitAmount()
        );
    }

    public BigDecimal actualPaymentAmount() {
        return amount.subtract(discountAmount);
    }

}
