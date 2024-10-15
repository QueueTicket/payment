package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.domain.checkout.Coupon;

public interface CouponClient {

    Coupon getCoupon(String couponId);

}
