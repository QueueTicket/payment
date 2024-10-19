package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaEntity;
import com.qticket.payment.domain.checkout.Benefit;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

// TODO customer, order, benefit, paymentOrder : 도메인 정리, 분류별 객체화
@Builder
public record PaymentEvent(
    Long id,
    Long customerId,
    String orderId,
    String orderName,
    Benefit benefit,
    List<PaymentOrder> paymentOrders,
    PaymentMethod method,
    String paymentKey,
    LocalDateTime approvedAt
) {

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
            benefit,
            method
        );
    }

    public BigDecimal benefitAmount() {
        if (benefit == null) {
            return BigDecimal.ZERO;
        }
        return benefit.benefitAmount();
    }

}
