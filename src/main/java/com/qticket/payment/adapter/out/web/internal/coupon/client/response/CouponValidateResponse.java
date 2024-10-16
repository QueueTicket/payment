package com.qticket.payment.adapter.out.web.internal.coupon.client.response;

public record CouponValidateResponse(
    String id,
    int discountAmount,
    DiscountPolicy discountPolicy,
    int maxDiscountAmount
) {

}
