package com.qticket.payment.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.application.port.in.CheckoutUseCase;
import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.application.port.out.PaymentExecutionPort;
import com.qticket.payment.domain.approve.ApprovalStatus;
import com.qticket.payment.domain.approve.PaymentApproveResult;
import com.qticket.payment.domain.approve.PaymentExecutionResult;
import com.qticket.payment.domain.approve.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.checkout.CheckoutResult;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@DisplayName("Service:PaymentApprove")
class PaymentApproveServiceTest extends PaymentTestHelper {

    private final CheckoutUseCase checkoutUseCase;
    private final AppliedBenefitService appliedBenefitService;
    private final PaymentApproveService paymentConfirmService;

    @SpyBean
    private PaymentExecutionPort paymentExecutionPort;

    public PaymentApproveServiceTest(
        CheckoutUseCase checkoutUseCase,
        AppliedBenefitService appliedBenefitService,
        PaymentApproveService paymentApproveService
    ) {
        this.checkoutUseCase = checkoutUseCase;
        this.appliedBenefitService = appliedBenefitService;
        this.paymentConfirmService = paymentApproveService;
    }

    private CheckoutResult checkout;
    private PaymentApproveCommand given;

    @BeforeEach
    void setUp() {
        checkout = checkoutUseCase.checkout(checkoutCommand);
        given = new PaymentApproveCommand(
            paymentKey,
            orderId,
            checkout.actualPaymentAmount().longValue()
        );
    }

    @Test
    @DisplayName("쿠폰 적용 시 결제 승인 성공")
    void paymentApproveSuccess() {
        // Given
        PaymentExecutionResult result = PaymentExecutionResult.builder()
            .paymentKey(paymentKey)
            .orderId(orderId)
            .status(ApprovalStatus.DONE.toPaymentStatus())
            .confirmDetails(new ApproveDetails(
                checkout.orderName(),
                checkout.amount(),
                PaymentMethod.EASY_PAY,
                LocalDateTime.now()
            )).build();
        given(paymentExecutionPort.execute(given)).willReturn(Mono.just(result));
        appliedBenefitService.appliedBenefit(orderId);

        // When
        PaymentApproveResult actual = paymentConfirmService.approve(given).block();
        PaymentJpaEntity paymentEvent = paymentJpaRepository.findByOrderId(orderId);

        // Then
        BigDecimal totalAmount = paymentEvent.totalAmount();

        assertAll(
            () -> assertThat(actual.paymentStatus()).isEqualTo(PaymentStatus.SUCCESS),
            () -> assertThat(totalAmount).isEqualByComparingTo(checkout.amount()),
            () -> assertThat(paymentEvent.getMethod()).isEqualTo(PaymentMethod.EASY_PAY),
            () -> assertThat(paymentEvent.getPaymentItemElements())
                .allMatch(order -> !order.isLedgerCompleted() && !order.isSettlementCompleted()),
            () -> assertThat(paymentEvent.getPaymentItemElements())
                .allMatch(it -> it.getStatus() == PaymentStatus.SUCCESS)
        );
    }

    @Test
    @DisplayName("결제 승인 실패")
    void paymentApproveFailed() {
        // Given
        PaymentExecutionResult result = PaymentExecutionResult.builder()
            .paymentKey(paymentKey)
            .orderId(orderId)
            .status(ApprovalStatus.ABORTED.toPaymentStatus())
            .failure(new Failure("E0001", "잔액 부족"))
            .build();

        given(paymentExecutionPort.execute(given)).willReturn(Mono.just(result));
        appliedBenefitService.appliedBenefit(orderId);

        // When
        PaymentApproveResult actual = paymentConfirmService.approve(given).block();
        PaymentJpaEntity paymentEvent = paymentJpaRepository.findByOrderId(orderId);
        paymentEvent.applyBenefit();

        // Then
        assertAll(
            () -> assertThat(actual.paymentStatus()).isEqualTo(PaymentStatus.FAILED),
            () -> assertThat(paymentEvent.getPaymentItemElements())
                .allMatch(it -> it.getStatus() == PaymentStatus.FAILED)
        );
    }

}
