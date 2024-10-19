package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.domain.checkout.Benefit;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record PaymentEvent(
    Long id,
    Long customerId,
    String orderId,
    String orderName,
    Benefit benefit,
    List<PaymentItem> paymentItems,
    PaymentMethod method,
    String paymentKey,
    LocalDateTime approvedAt
) {

    public BigDecimal totalAmount() {
        return paymentItems.stream()
            .map(PaymentItem::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PaymentJpaEntity toEntity() {
        return PaymentJpaEntity.of(
            customerId,
            orderId,
            orderName,
            paymentItems,
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
