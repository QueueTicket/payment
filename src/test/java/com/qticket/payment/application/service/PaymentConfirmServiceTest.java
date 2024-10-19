package com.qticket.payment.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaEntity;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.application.port.in.CheckoutUseCase;
import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.application.port.out.PaymentExecutionPort;
import com.qticket.payment.domain.checkout.CheckoutResult;
import com.qticket.payment.domain.confirm.ConfirmStatus;
import com.qticket.payment.domain.confirm.PaymentConfirmResult;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import com.qticket.payment.domain.confirm.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@DisplayName("Service:PaymentConfirm")
class PaymentConfirmServiceTest extends PaymentTestHelper {

    private final CheckoutUseCase checkoutUseCase;
    private final AppliedCouponService appliedCouponService;
    private final PaymentConfirmService paymentConfirmService;

    @SpyBean
    private PaymentExecutionPort paymentExecutionPort;

    public PaymentConfirmServiceTest(
        CheckoutUseCase checkoutUseCase,
        AppliedCouponService appliedCouponService,
        PaymentConfirmService paymentConfirmService
    ) {
        this.checkoutUseCase = checkoutUseCase;
        this.appliedCouponService = appliedCouponService;
        this.paymentConfirmService = paymentConfirmService;
    }

    @Test
    @DisplayName("쿠폰 적용 시 결제 승인 성공")
    void paymentConfirmSuccess() {
        // Given
        CheckoutResult checkout = checkoutUseCase.checkout(checkoutCommand);
        String paymentKey = UUID.randomUUID().toString();
        PaymentConfirmCommand given = new PaymentConfirmCommand(
            paymentKey,
            orderId,
            checkout.actualPaymentAmount().longValue()
        );

        LocalDateTime approvedAt = LocalDateTime.now();
        PaymentExecutionResult result = PaymentExecutionResult.of(
            paymentKey,
            orderId,
            ConfirmStatus.DONE,
            new ApproveDetails(
                checkout.orderName(),
                checkout.amount(),
                PaymentMethod.EASY_PAY,
                approvedAt
            ),
            null
        );
        given(paymentExecutionPort.execute(given)).willReturn(Mono.just(result));
        appliedCouponService.appliedCoupon(orderId);

        // When
        PaymentConfirmResult actual = paymentConfirmService.confirm(given).block();
        PaymentEventJpaEntity paymentEvent = paymentEventJpaRepository.findByOrderId(orderId);

        // Then
        BigDecimal totalAmount = paymentEvent.getPaymentOrders().stream()
            .map(PaymentOrderJpaEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertAll(
            () -> assertThat(actual.paymentStatus()).isEqualTo(PaymentStatus.SUCCESS),
            () -> assertThat(totalAmount).isEqualByComparingTo(checkout.amount()),
            () -> assertThat(paymentEvent.getMethod()).isEqualTo(PaymentMethod.EASY_PAY),
            () -> assertThat(paymentEvent.getPaymentOrders())
                .allMatch(order -> !order.isLedgerCompleted() && !order.isSettlementCompleted()),
            () -> assertThat(paymentEvent.getPaymentOrders())
                .allMatch(it -> it.getStatus() == PaymentStatus.SUCCESS)
        );
    }

    @Test
    @DisplayName("결제 승인 실패")
    void paymentConfirmFailed() {
        // Given
        CheckoutResult checkout = checkoutUseCase.checkout(checkoutCommand);
        String paymentKey = UUID.randomUUID().toString();
        PaymentConfirmCommand given = new PaymentConfirmCommand(
            paymentKey,
            orderId,
            checkout.actualPaymentAmount().longValue()
        );

        PaymentExecutionResult result = PaymentExecutionResult.of(
            paymentKey,
            orderId,
            ConfirmStatus.ABORTED,
            null,
            new Failure("E0001", "잔액 부족")
        );
        given(paymentExecutionPort.execute(given)).willReturn(Mono.just(result));
        appliedCouponService.appliedCoupon(orderId);

        // When
        PaymentConfirmResult actual = paymentConfirmService.confirm(given).block();
        PaymentEventJpaEntity paymentEvent = paymentEventJpaRepository.findByOrderId(orderId);
        paymentEvent.applyBenefit();

        // Then
        assertAll(
            () -> assertThat(actual.paymentStatus()).isEqualTo(PaymentStatus.FAILED),
            () -> assertThat(paymentEvent.getPaymentOrders())
                .allMatch(it -> it.getStatus() == PaymentStatus.FAILED)
        );
    }

}
