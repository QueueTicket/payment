package com.qticket.payment.adapter.out.web.coupon;

import com.qticket.payment.adapter.out.web.coupon.client.CouponClient;
import com.qticket.payment.application.port.out.LoadCouponPort;
import com.qticket.payment.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponWebAdapter implements LoadCouponPort {

    private final CouponClient couponClient;

    @Override
    public Coupon getCoupon(String couponId) {
        return couponClient.getCoupon(couponId);
    }

}
