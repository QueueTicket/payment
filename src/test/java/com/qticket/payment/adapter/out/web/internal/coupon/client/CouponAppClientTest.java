package com.qticket.payment.adapter.out.web.internal.coupon.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import com.qticket.payment.config.wiremock.InternalClientTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Adapter:Out:Web:Internal:Coupon")
class CouponAppClientTest extends InternalClientTestBase {

    private final CouponAppClient couponAppClient;

    public CouponAppClientTest(CouponAppClient couponAppClient) {
        this.couponAppClient = couponAppClient;
    }

    @Test
    @DisplayName("쿠폰 유효성 검증 API")
    void validateCoupon() {
        // Given
        final Long userId = 1L;
        final String couponId = "coupon-id";
        final String concertId = "concert-id";
        final Long totalPrice = 120_000L;

        // When
        CouponValidateResponse actual = couponAppClient.validateCoupon(userId, couponId, concertId, totalPrice);

        // Then
        assertThat(actual.discountPolicy()).isEqualTo(DiscountPolicy.PERCENTAGE);
    }

}
