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
    @DisplayName("쿠폰의 할인 적용 가능 금액 계산")
    void ex() {
        // Given
        Coupon coupon = Coupon.of(
            1L,
            new CouponValidateResponse(
                "new-coupon",
                20,
                DiscountPolicy.PERCENTAGE,
                10_000
            )
        );
        final BigDecimal originPrice = BigDecimal.valueOf(130_000);

        // When
        BigDecimal amount = coupon.applicableDiscountAmount(originPrice);

        // Then
        assertThat(amount).isEqualTo(BigDecimal.valueOf(10_000));
    }

}
