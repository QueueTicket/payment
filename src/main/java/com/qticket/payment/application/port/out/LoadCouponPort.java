package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.checkout.Coupon;
import com.qticket.payment.domain.checkout.Reservation;

public interface LoadCouponPort {

    Coupon getCoupon(String couponId, Reservation reservation);

}
