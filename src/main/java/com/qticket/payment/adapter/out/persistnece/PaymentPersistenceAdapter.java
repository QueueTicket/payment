package com.qticket.payment.adapter.out.persistnece;

import static com.qticket.payment.domain.approve.StatusChangedReason.PAYMENT_APPROVE_STARTED;
import static com.qticket.payment.domain.approve.StatusChangedReason.PAYMENT_APPROVE_SUCCESS;
import static com.qticket.payment.domain.approve.StatusChangedReason.PAYMENT_APPROVE_UNKNOWN;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentItemHistoryJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentItemJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemHistoryJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemJpaEntities;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.application.port.out.AppliedBenefitPort;
import com.qticket.payment.application.port.out.PaymentStatusUpdatePort;
import com.qticket.payment.application.port.out.PaymentValidationPort;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.domain.payment.PaymentEvent;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.adapter.persistence.InValidAmountException;
import com.qticket.payment.exception.application.InValidPaymentStatusException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements
    SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, AppliedBenefitPort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentItemJpaRepository paymentItemJpaRepository;
    private final PaymentItemHistoryJpaRepository paymentItemHistoryJpaRepository;

    @Override
    @Transactional
    public void save(PaymentEvent paymentEvent) {
        paymentJpaRepository.save(paymentEvent.toEntity());
    }

    @Override
    @Transactional
    public Mono<Void> updatePaymentStatusToApproveProcessing(String orderId, String paymentKey) {
        PaymentJpaEntity paymentEvent = paymentJpaRepository.findByOrderId(orderId);
        PaymentItemJpaEntities paymentItems = paymentEvent.extractChangeableProcessingOrders();

        checkIsChangeableInPaymentProcessing(paymentItems);
        paymentEvent.registerPaymentKey(paymentKey);
        updatePaymentStatus(paymentItems, PaymentStatus.PROCESSING, PAYMENT_APPROVE_STARTED.getDescription());

        return Mono.defer(Mono::empty).then();
    }

    private void checkIsChangeableInPaymentProcessing(PaymentItemJpaEntities paymentItems) {
        paymentItems.checkIsChangeableInProcessing();
    }

    public void updatePaymentStatus(PaymentItemJpaEntities paymentItems, PaymentStatus newStatus, String reason) {
        savePaymentStatusChangeHistory(paymentItems, newStatus, reason);
        paymentItems.updateStatus(newStatus);
    }

    public void savePaymentStatusChangeHistory(
        PaymentItemJpaEntities paymentItems,
        PaymentStatus updatedStatus,
        String reason
    ) {
        List<PaymentItemHistoryJpaEntity> histories = paymentItems.toHistories(updatedStatus, reason);
        paymentItemHistoryJpaRepository.saveAll(histories);
    }

    @Override
    public Mono<Void> validateApprovalAmount(String orderId, Long approvedAmount) {
        return Mono.fromRunnable(() -> {
            BigDecimal paymentAmount = paymentItemJpaRepository.findPaymentAmount(orderId);
            if (isPaymentAmountNotMatched(approvedAmount, paymentAmount)) {
                throw new InValidAmountException(orderId, approvedAmount, paymentAmount);
            }
        });
    }

    private boolean isPaymentAmountNotMatched(Long approvedAmount, BigDecimal paymentAmount) {
        return paymentAmount.compareTo(BigDecimal.valueOf(approvedAmount)) != 0;
    }

    @Override
    @Transactional
    public Mono<Void> updatePaymentStatus(PaymentStatusUpdateCommand command) {
        PaymentJpaEntity paymentEvent = paymentJpaRepository.findByOrderId(command.getOrderId());
        switch (command.getStatus()) {
            case SUCCESS -> updatePaymentStatusToSuccess(paymentEvent, command);
            case FAILED -> updatePaymentStatusToFailed(paymentEvent, command);
            case UNKNOWN_APPROVE -> updatePaymentStatusToUnknown(paymentEvent, command);
            default -> throw new InValidPaymentStatusException(
                command.getOrderId(),
                command.getPaymentKey(),
                command.getStatus()
            );
        }
        return Mono.defer(Mono::empty).then();
    }

    public void updatePaymentStatusToSuccess(
        PaymentJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        paymentEvent.updatePaymentDetails(command.getApproveDetails());
        updatePaymentStatus(
            paymentEvent.getPaymentItems(),
            command.getStatus(),
            PAYMENT_APPROVE_SUCCESS.getDescription()
        );
    }

    private void updatePaymentStatusToFailed(
        PaymentJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        paymentEvent.updatePaymentFailCount();
        updatePaymentStatus(
            paymentEvent.getPaymentItems(),
            command.getStatus(),
            command.getFailure().message()
        );
    }

    private void updatePaymentStatusToUnknown(
        PaymentJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        updatePaymentStatus(
            paymentEvent.getPaymentItems(),
            command.getStatus(),
            PAYMENT_APPROVE_UNKNOWN.getDescription()
        );
    }

    @Override
    @Transactional
    public Mono<Void> updateBenefitApplied(String orderId) {
        PaymentJpaEntity payment = paymentJpaRepository.findByOrderId(orderId);
        payment.applyBenefit();
        return Mono.defer(Mono::empty).then();
    }

}
