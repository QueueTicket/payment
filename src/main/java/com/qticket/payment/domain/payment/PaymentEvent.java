package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaEntity;
import com.qticket.payment.domain.checkout.Coupon;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

// TODO customer, order, benefit, paymentOrder : 도메인 정리, 분류별 객체화
@Builder(access = AccessLevel.PRIVATE)
public record PaymentEvent(
    Long id,
    Long customerId,
    String orderId,
    String orderName,
    Coupon coupon,
    List<PaymentOrder> paymentOrders,
    PaymentMethod method,
    String paymentKey,
    LocalDateTime approvedAt
    //TODO PaymentBenefit benefits -> coupon id, discount amount,  policy ... etc
) {

    public static PaymentEvent prepareEasyPayment(
        Long customerId,
        String orderId,
        String orderName,
        Coupon coupon,
        List<PaymentOrder> paymentOrders
    ) {
        return PaymentEvent.builder()
            .customerId(customerId)
            .orderId(orderId)
            .orderName(orderName)
            .coupon(coupon)
            .method(PaymentMethod.EASY_PAY)
            .paymentOrders(paymentOrders)
            .build();
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
            paymentOrders,
            coupon,
            method
        );
    }

    public BigDecimal benefitAmount() {
        return coupon.benefitAmount();
    }

}
