package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${internal.instance.name.coupon}", url = "${wiremock.base.url}")
public interface CouponAppClient {

    @GetMapping("/coupons/validate")
    CouponValidateResponse validateCoupon(
        @RequestParam("userId") Long userId,
        @RequestParam("couponId") String couponId,
        @RequestParam("eventId") String concertId,
        @RequestParam("price") Long totalPrice
    );

}
