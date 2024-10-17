package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import java.math.BigDecimal;

public record Coupon(
    String id,
    Long customerId,
    BigDecimal discountAmount,
    BigDecimal maxDiscountAmount,
    DiscountPolicy discountPolicy
) {

    public static Coupon of(Long customerId, CouponValidateResponse response) {
        return new Coupon(
            response.id(),
            customerId,
            java.math.BigDecimal.valueOf(response.discountAmount()),
            java.math.BigDecimal.valueOf(response.maxDiscountAmount()),
            response.discountPolicy()
        );
    }

    public BigDecimal applicableDiscountAmount(BigDecimal originPrice) {
        return discountPolicy.calculateDiscount(originPrice, discountAmount, maxDiscountAmount);
    }

}
