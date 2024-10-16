package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import com.qticket.payment.domain.checkout.Reservation;
import org.springframework.stereotype.Component;

@Component
public class FakeCouponClient implements CouponMockClient {

    @Override
    public CouponValidateResponse validateCoupon(String couponId, Reservation reservation) {
        return new CouponValidateResponse(
            couponId,
            17_000,
            DiscountPolicy.FIXED,
            10_000
        );
    }

}
