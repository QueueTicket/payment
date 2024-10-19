package com.qticket.payment.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.domain.checkout.CheckoutResult;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

@DisplayName("Service:Checkout")
class CheckoutServiceTest extends PaymentTestHelper {

    private final CheckoutService checkoutService;

    public CheckoutServiceTest(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Test
    @DisplayName("결제 저장")
    void save() {
        // When
        CheckoutResult actual = checkoutService.checkout(checkoutCommand);

        // Then
        PaymentJpaEntity paymentEvent = paymentJpaRepository.findByOrderId(orderId);

        assertThat(actual.amount()).isEqualTo(new BigDecimal(120_000));
        assertFalse(paymentEvent.isCompleted());
        assertThat(paymentEvent.getPaymentItems())
            .allMatch(order -> !order.isLedgerCompleted() && !order.isSettlementCompleted());
    }

    @Test
    @DisplayName("중복 결제 시 예외")
    void throwException_WhenDuplicatedPaymentEvent() {
        // Given
        checkoutService.checkout(checkoutCommand);

        // When & Then
        assertThatExceptionOfType(DataIntegrityViolationException.class)
            .isThrownBy(() -> checkoutService.checkout(checkoutCommand));
    }

}
