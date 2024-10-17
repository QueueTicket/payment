package com.qticket.payment.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.qticket.payment.adapter.in.web.view.request.CheckoutRequest;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.global.utils.IdempotencyGenerator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Utils:IdempotencyGenerator")
class IdempotencyGeneratorTest {

    @Test
    @DisplayName("멱등성 키 생성")
    void generateEqualsIdempotencyKey() {
        // Given
        CheckoutRequest checkoutRequest = generateMockCheckoutRequest(1L, "new-coupon", "concert-1");

        // When
        String idempotencyKey1 = IdempotencyGenerator.generate(checkoutRequest);
        String idempotencyKey2 = IdempotencyGenerator.generate(checkoutRequest);

        // Then
        assertEquals(idempotencyKey1, idempotencyKey2);
    }

    @Test
    @DisplayName("멱등성 키 생성")
    void generateDifferentIdempotencyKey() {
        // Given
        CheckoutRequest checkoutRequest1 = generateMockCheckoutRequest(1L, "new-coupon", "concert-1");
        CheckoutRequest checkoutRequest2 = generateMockCheckoutRequest(2L, "event-coupon", "concert-2");

        // When
        String idempotencyKey1 = IdempotencyGenerator.generate(checkoutRequest1);
        String idempotencyKey2 = IdempotencyGenerator.generate(checkoutRequest2);

        // Then
        assertNotEquals(idempotencyKey1, idempotencyKey2);
    }

    private CheckoutRequest generateMockCheckoutRequest(Long customerId, String couponId, String concertId) {
        return new CheckoutRequest(
            customerId,
            couponId,
            concertId,
            List.of("seat-A", "seat-B"),
            PaymentMethod.EASY_PAY
        );
    }

}
