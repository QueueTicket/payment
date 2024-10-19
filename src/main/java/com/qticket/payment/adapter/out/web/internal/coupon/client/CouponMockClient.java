package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.domain.checkout.Reservation;

public interface CouponMockClient {

    CouponValidateResponse validateCoupon(String couponId, Reservation reservation);

}
