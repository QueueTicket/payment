package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.BenefitJpaEntity;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record Benefit(
    String id,
    String paymentEventId,
    BigDecimal totalPrice,
    BigDecimal benefitAmount
) {

    public static Benefit of(CouponValidateResponse response, BigDecimal totalPrice) {
        BigDecimal discountAmount = BigDecimal.valueOf(response.discountAmount());
        BigDecimal maxDiscountAmount = BigDecimal.valueOf(response.maxDiscountAmount());
        BigDecimal applicableDiscountAmount = response.discountPolicy()
            .calculateDiscount(
                totalPrice,
                discountAmount,
                maxDiscountAmount
            );
        return Benefit.builder()
            .id(response.id())
            .totalPrice(totalPrice)
            .benefitAmount(applicableDiscountAmount)
            .build();
    }

    public BenefitJpaEntity toEntity() {
        return new BenefitJpaEntity(
            id,
            benefitAmount
        );
    }

}
