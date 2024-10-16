package com.qticket.payment.adapter.out.web.internal.coupon;

import com.qticket.payment.adapter.out.web.internal.coupon.client.CouponAppClient;
import com.qticket.payment.adapter.out.web.internal.coupon.client.CouponMockClient;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.application.port.out.LoadCouponPort;
import com.qticket.payment.domain.checkout.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponWebAdapter implements LoadCouponPort {

    @Value("${feature.toggle.use.internal.coupon-app:false}")
    private boolean useInternalClient;

    private final CouponAppClient couponAppClient;
    private final CouponMockClient couponMockClient;

    @Override
    public CouponValidateResponse getCoupon(String couponId, Ticket ticket) {
        if (useInternalClient) {
            return couponAppClient.validateCoupon(
                ticket.userId(),
                couponId,
                ticket.id(),
                ticket.totalPrice()
            );
        }
        return couponMockClient.validateCoupon(couponId, ticket);
    }

}
