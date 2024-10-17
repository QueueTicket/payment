package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import java.math.BigDecimal;

public record Coupon(
    String id,
    Long customerId,
    BigDecimal discountAmount,
    DiscountPolicy discountPolicy,
    BigDecimal maxDiscountAmount
) {

    public static Coupon of(Long customerId, CouponValidateResponse response) {
        return new Coupon(
            response.id(),
            customerId,
            BigDecimal.valueOf(response.discountAmount()),
            response.discountPolicy(),
            BigDecimal.valueOf(response.maxDiscountAmount())
        );
    }

}
