package com.qticket.payment.application.port.out;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.domain.checkout.Ticket;

public interface LoadCouponPort {

    CouponValidateResponse getCoupon(String couponId, Ticket ticket);

}
