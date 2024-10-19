package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.BenefitJpaEntity;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import java.math.BigDecimal;
import lombok.Builder;

// todo benefit : id, payment_id, applicableDiscountAmount, coupon
@Builder
public record Coupon(
    String id,
    String paymentEventId,
    BigDecimal totalPrice,
    BigDecimal benefitAmount,
    BigDecimal discountAmount, // TODO benefit table에서 관리하는 데이터가 아니면 제거
    BigDecimal maxDiscountAmount, // TODO benefit table에서 관리하는 데이터가 아니면 제거
    DiscountPolicy discountPolicy //TODO benefit table에서 관리하는 데이터가 아니면 제거
) {

    public static Coupon of(CouponValidateResponse response, BigDecimal totalPrice) {
        BigDecimal discountAmount = BigDecimal.valueOf(response.discountAmount());
        BigDecimal maxDiscountAmount = BigDecimal.valueOf(response.maxDiscountAmount());
        BigDecimal applicableDiscountAmount = response.discountPolicy()
            .calculateDiscount(
                totalPrice,
                discountAmount,
                maxDiscountAmount
            );
        return Coupon.builder()
            .id(response.id())
            .totalPrice(totalPrice)
            .benefitAmount(applicableDiscountAmount)
            .discountAmount(discountAmount)
            .maxDiscountAmount(maxDiscountAmount)
            .discountPolicy(response.discountPolicy())
            .build();
    }

    public BenefitJpaEntity toEntity() {
        return new BenefitJpaEntity(
            id,
            benefitAmount,
            discountPolicy
        );
    }

}
