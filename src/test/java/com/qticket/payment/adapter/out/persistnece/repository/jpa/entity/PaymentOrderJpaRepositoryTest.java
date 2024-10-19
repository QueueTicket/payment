package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Adapter:Out:Persistence:Repository:Jpa:PaymentOrder")
class PaymentOrderJpaRepositoryTest extends PaymentRepositoryTestHelper {

    @Test
    @DisplayName("혜택이 적용된 결제 금액 조회")
    void findPaymentAmountWithBenefit() {
        // When
        PaymentEventJpaEntity paymentEventJpaEntity = paymentEvent.toEntity();
        paymentEventJpaEntity.applyBenefit();
        paymentEventJpaRepository.save(paymentEventJpaEntity);
        paymentOrderJpaRepository.saveAll(paymentEventJpaEntity.getPaymentOrders());

        // Then
        BigDecimal paymentAmount = paymentOrderJpaRepository.findPaymentAmount(paymentEvent.orderId());
        BigDecimal totalPrice = paymentEvent.totalAmount();
        BigDecimal discountAmount = paymentEvent.benefitAmount();
        assertThat(paymentAmount).isEqualByComparingTo(totalPrice.subtract(discountAmount));
    }

    @Test
    @DisplayName("혜택이 적용되지 않은 결제 금액 조회")
    void findPaymentAmountWithoutBenefit() {
        // When
        paymentRepository.save(paymentEvent);

        // Then
        BigDecimal paymentAmount = paymentOrderJpaRepository.findPaymentAmount(paymentEvent.orderId());
        assertThat(paymentAmount).isEqualByComparingTo(paymentEvent.totalAmount());
    }

}
