package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import com.qticket.payment.domain.checkout.Ticket;
import org.springframework.stereotype.Component;

@Component
public class FakeCouponClient implements CouponMockClient {

    @Override
    public CouponValidateResponse validateCoupon(String couponId, Ticket ticket) {
        return new CouponValidateResponse(
            couponId,
            17_000,
            DiscountPolicy.FIXED,
            10_000
        );
    }

}
