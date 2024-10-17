package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PaymentEvent(
    Long id,
    Long customerId,
    String orderId,
    String orderName,
    String couponId,
    BigDecimal discountAmount,
    PaymentMethod method,
    List<PaymentOrder> paymentOrders,
    String paymentKey,
    LocalDateTime approvedAt
) {

    private PaymentEvent(
        Long customerId,
        String orderId,
        String orderName,
        String couponId,
        BigDecimal discountedAmount,
        PaymentMethod method,
        List<PaymentOrder> paymentOrders
    ) {
        this(
            0L,
            customerId,
            orderId,
            orderName,
            couponId,
            discountedAmount,
            method,
            paymentOrders,
            null,
            null
        );
    }

    public static PaymentEvent of(
        Long customerId,
        String orderId,
        String orderName,
        String couponId,
        BigDecimal discountedAmount,
        PaymentMethod method,
        List<PaymentOrder> paymentOrders
    ) {
        return new PaymentEvent(
            customerId,
            orderId,
            orderName,
            couponId,
            discountedAmount,
            method,
            paymentOrders
        );
    }

    public BigDecimal totalAmount() {
        return paymentOrders.stream()
            .map(PaymentOrder::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PaymentEventJpaEntity toEntity() {
        return PaymentEventJpaEntity.of(
            customerId,
            orderId,
            orderName,
            couponId,
            discountAmount,
            method,
            paymentOrders
        );
    }

}
