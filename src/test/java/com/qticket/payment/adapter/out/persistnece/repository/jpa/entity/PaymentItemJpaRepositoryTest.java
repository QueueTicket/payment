package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Adapter:Out:Persistence:Repository:Jpa:PaymentItem")
class PaymentItemJpaRepositoryTest extends PaymentRepositoryTestHelper {

    @Test
    @DisplayName("혜택이 적용된 결제 금액 조회")
    void findPaymentAmountWithBenefit() {
        // When
        PaymentJpaEntity paymentJpaEntity = paymentEvent.toEntity();
        paymentJpaEntity.applyBenefit();
        paymentJpaRepository.save(paymentJpaEntity);
//        paymentItemJpaRepository.saveAll(paymentJpaEntity.getPaymentItems());

        // Then
        BigDecimal paymentAmount = paymentItemJpaRepository.findPaymentAmount(paymentEvent.orderId());
        BigDecimal totalPrice = paymentEvent.totalAmount();
        BigDecimal discountAmount = paymentEvent.benefitAmount();
        assertThat(paymentAmount).isEqualByComparingTo(totalPrice.subtract(discountAmount));
    }

    @Test
    @DisplayName("혜택이 적용되지 않은 결제 금액 조회")
    void findPaymentAmountWithoutBenefit() {
        // When
        PaymentJpaEntity paymentJpaEntity = paymentEvent.toEntity();
        paymentJpaRepository.save(paymentJpaEntity);
//        paymentItemJpaRepository.saveAll(paymentJpaEntity.getPaymentItems());

        // Then
        BigDecimal paymentAmount = paymentItemJpaRepository.findPaymentAmount(paymentEvent.orderId());
        assertThat(paymentAmount).isEqualByComparingTo(paymentEvent.totalAmount());
    }

}
