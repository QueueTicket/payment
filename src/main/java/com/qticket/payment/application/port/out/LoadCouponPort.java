package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.Coupon;

public interface LoadCouponPort {

    Coupon getCoupon(String couponId);

}
