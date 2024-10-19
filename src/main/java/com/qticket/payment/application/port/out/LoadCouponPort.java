package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.checkout.Benefit;
import com.qticket.payment.domain.checkout.Reservation;

public interface LoadCouponPort {

    Benefit getCoupon(String couponId, Reservation reservation);

}
