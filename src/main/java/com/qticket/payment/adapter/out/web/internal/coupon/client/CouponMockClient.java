package com.qticket.payment.adapter.out.web.internal.coupon.client;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.domain.checkout.Ticket;

public interface CouponMockClient {

    // /coupons/validate -> user_id, coupon_id, event_id(concert_id), totalPrice
    // maxDiscountAmount는 discountAmount보다 클 수 없음
    // DiscountPolicy에 따라서 할인 금액/할인률을 구분
    CouponValidateResponse validateCoupon(String couponId, Ticket ticket);

}
