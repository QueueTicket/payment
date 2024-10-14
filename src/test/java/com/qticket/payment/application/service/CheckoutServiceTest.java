package com.qticket.payment.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.qticket.payment.adapter.out.persistnece.entity.PaymentEventJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.PaymentEventJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.PaymentOrderJpaRepository;
import com.qticket.payment.application.port.in.CheckoutCommand;
import com.qticket.payment.base.SpringBootTestBase;
import com.qticket.payment.domain.CheckoutResult;
import com.qticket.payment.domain.payment.PaymentMethod;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

@DisplayName("Service:Checkout")
class CheckoutServiceTest extends SpringBootTestBase {

    private final CheckoutService checkoutService;
    private final PaymentEventJpaRepository paymentEventJpaRepository;
    private final PaymentOrderJpaRepository paymentOrderJpaRepository;

    public CheckoutServiceTest(
        CheckoutService checkoutService,
        PaymentEventJpaRepository paymentEventJpaRepository,
        PaymentOrderJpaRepository paymentOrderJpaRepository
    ) {
        this.checkoutService = checkoutService;
        this.paymentEventJpaRepository = paymentEventJpaRepository;
        this.paymentOrderJpaRepository = paymentOrderJpaRepository;
    }

    private final String orderId = UUID.randomUUID().toString();

    @AfterEach
    void tearDown() {
        paymentOrderJpaRepository.deleteAll();
        paymentEventJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("결제 저장")
    void save() {
        // Given
        CheckoutCommand given = new CheckoutCommand(
            1L,
            "consert-id",
            List.of("seat-a"),
            "coupon-id",
            PaymentMethod.EASY_PAY,
            orderId
        );

        // When
        CheckoutResult actual = checkoutService.checkout(given);

        // Then
        PaymentEventJpaEntity paymentEvent = paymentEventJpaRepository.findByOrderId(orderId);

        assertThat(actual.amount()).isEqualTo(new BigDecimal(45_000));
        assertFalse(paymentEvent.isCompleted());
        assertThat(paymentEvent.getPaymentOrders())
            .allMatch(order -> !order.isLedgerCompleted() && !order.isSettlementCompleted());
    }

    @Test
    @DisplayName("중복 결제 시 예외")
    void throwException_WhenDuplicatedPaymentEvent() {
        // Given
        CheckoutCommand given = new CheckoutCommand(
            1L,
            "consert-id",
            List.of("seat-a"),
            "coupon-id",
            PaymentMethod.EASY_PAY,
            orderId
        );
        checkoutService.checkout(given);

        // When & Then
        assertThatExceptionOfType(DataIntegrityViolationException.class)
            .isThrownBy(() -> checkoutService.checkout(given));
    }

}
