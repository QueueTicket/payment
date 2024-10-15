package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.domain.checkout.Coupon;
import org.springframework.stereotype.Component;

@Component
public class MockCouponClient implements CouponClient {

    @Override
    public Coupon getCoupon(String couponId) {
        return new Coupon(couponId, "신규 회원 쿠폰", true);
    }

}
