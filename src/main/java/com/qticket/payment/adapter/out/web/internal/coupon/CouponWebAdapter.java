package com.qticket.payment.adapter.out.web.internal.coupon;

import com.qticket.payment.adapter.out.web.internal.coupon.client.CouponAppClient;
import com.qticket.payment.adapter.out.web.internal.coupon.client.CouponMockClient;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.application.port.out.LoadCouponPort;
import com.qticket.payment.domain.checkout.Coupon;
import com.qticket.payment.domain.checkout.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponWebAdapter implements LoadCouponPort {

    private final CouponAppClient couponAppClient;
    private final CouponMockClient couponMockClient;

    @Value("${feature.toggle.use.internal.coupon-app:false}")
    private boolean useInternalClient;

    @Override
    public Coupon getCoupon(String couponId, Reservation reservation) {
        if (useInternalClient) {
            CouponValidateResponse response = couponAppClient.validateCoupon(
                reservation.customerId(),
                couponId,
                reservation.concertId(),
                reservation.totalPrice().longValueExact()
            );

            return Coupon.of(reservation.customerId(), response);
        }
        return Coupon.of(reservation.customerId(), couponMockClient.validateCoupon(couponId, reservation));
    }

}
