package com.qticket.payment.adapter.out.web.coupon.client;

import com.qticket.payment.domain.Coupon;

public interface CouponClient {

    Coupon getCoupon(String couponId);

}
