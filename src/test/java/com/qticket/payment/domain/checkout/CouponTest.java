package com.qticket.payment.domain.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:Coupon")
class CouponTest {

    @Test
    @DisplayName("적용 가능한 최대 쿠폰 할인 금액 계산")
    void calculateApplicableMaximumCouponDiscountAmount() {
        // Given
        Coupon coupon = Coupon.of(
            new CouponValidateResponse(
                "new-coupon",
                20,
                DiscountPolicy.PERCENTAGE,
                10_000
            ),
            BigDecimal.valueOf(110_000)
        );

        // When
        BigDecimal amount = coupon.benefitAmount();

        // Then
        assertThat(amount).isEqualTo(BigDecimal.valueOf(10_000));
    }

}
