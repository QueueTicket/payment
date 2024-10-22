package com.qticket.payment.adapter.in.web.api.response;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.BenefitJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.domain.payment.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record PaymentResponse(
    String orderId,
    Long customerId,
    String couponId,
    ReservationResponse reservation,
    PaymentMethod method,
    BigDecimal totalAmount,
    BigDecimal discountAmount,
    BigDecimal paymentAmount,
    LocalDateTime approveAt
) {

    public static PaymentResponse of(PaymentJpaEntity paymentJpaEntity) {
        ReservationResponse reservationResponse = ReservationResponse.of(paymentJpaEntity.getPaymentItems());

        return new PaymentResponse(
            paymentJpaEntity.getOrderId(),
            paymentJpaEntity.getCustomerId(),
            couponId(paymentJpaEntity.getBenefit()),
            reservationResponse,
            paymentJpaEntity.getMethod(),
            paymentJpaEntity.totalAmount(),
            discountAmount(paymentJpaEntity),
            paymentJpaEntity.paymentAmount(),
            paymentJpaEntity.getApprovedAt()
        );
    }

    private static BigDecimal discountAmount(PaymentJpaEntity paymentJpaEntity) {
        if (paymentJpaEntity.isBenefitApplied()) {
            return paymentJpaEntity.getBenefit().getDiscountAmount();
        }
        return BigDecimal.ZERO;
    }

    private static String couponId(BenefitJpaEntity benefit) {
        if (Objects.isNull(benefit)) {
            return null;
        }
        return benefit.getCouponId();
    }

}
