package com.qticket.payment.adapter.out.persistnece;

import static com.qticket.payment.domain.approve.StatusChangedReason.PAYMENT_APPROVE_STARTED;
import static com.qticket.payment.domain.approve.StatusChangedReason.PAYMENT_APPROVE_SUCCESS;
import static com.qticket.payment.domain.approve.StatusChangedReason.PAYMENT_APPROVE_UNKNOWN;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentItemHistoryJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentItemJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemHistoryJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.application.port.out.AppliedBenefitPort;
import com.qticket.payment.application.port.out.PaymentStatusUpdatePort;
import com.qticket.payment.application.port.out.PaymentValidationPort;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.domain.payment.PaymentEvent;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.application.InValidPaymentStatusException;
import com.qticket.payment.exception.persistence.InValidAmountException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements
    SavePaymentPort, PaymentStatusUpdatePort, PaymentValidationPort, AppliedBenefitPort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentItemJpaRepository paymentItemJpaRepository;
    private final PaymentItemHistoryJpaRepository paymentItemHistoryJpaRepository;

    @Override
    public void save(PaymentEvent paymentEvent) {
        paymentJpaRepository.save(paymentEvent.toEntity());
    }

    @Override
    public void updateStatusToProcessing(String orderId, String paymentKey) {
        PaymentJpaEntity paymentEvent = paymentJpaRepository.findByOrderId(orderId);
        List<PaymentItemJpaEntity> paymentItems = paymentEvent.extractChangeableProcessingOrders();

        checkIsChangeableInProcessingPaymentItem(paymentItems);
        registerPaymentKey(paymentEvent, paymentKey);
        updateStatus(paymentItems, PaymentStatus.PROCESSING, PAYMENT_APPROVE_STARTED.getDescription());
    }

    private void registerPaymentKey(PaymentJpaEntity paymentEvent, String paymentKey) {
        paymentEvent.registerPaymentKey(paymentKey);
    }

    private void checkIsChangeableInProcessingPaymentItem(List<PaymentItemJpaEntity> paymentItems) {
        paymentItems.forEach(PaymentItemJpaEntity::checkIsChangeableInProcessing);
    }

    public void saveChangePaymentStatusHistory(
        List<PaymentItemJpaEntity> paymentItems,
        PaymentStatus updatedStatus,
        String reason
    ) {
        List<PaymentItemHistoryJpaEntity> histories = paymentItems.stream()
            .map(it -> PaymentItemHistoryJpaEntity.of(it, updatedStatus, reason))
            .toList();

        paymentItemHistoryJpaRepository.saveAll(histories);
    }

    public void updateStatus(List<PaymentItemJpaEntity> paymentItems, PaymentStatus status, String reason) {
        paymentItems.forEach(it -> it.updateStatus(status));
        saveChangePaymentStatusHistory(paymentItems, status, reason);
    }

    @Override
    public void isValidPaymentAmount(String orderId, Long amount) {
        BigDecimal totalAmount = paymentItemJpaRepository.findPaymentAmount(orderId);
        if (isTotalAmountNotMatched(amount, totalAmount)) {
            throw new InValidAmountException(orderId, amount, totalAmount);
        }
    }

    private boolean isTotalAmountNotMatched(Long amount, BigDecimal totalAmount) {
        return totalAmount.compareTo(BigDecimal.valueOf(amount)) != 0;
    }

    @Override
    public void updatePaymentStatus(PaymentStatusUpdateCommand command) {
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
    }

    public void updatePaymentStatusToSuccess(
        PaymentJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        List<PaymentItemJpaEntity> paymentItems = paymentEvent.getPaymentItems();
        paymentEvent.updatePaymentDetails(command.getApproveDetails());
        updateStatus(paymentItems, command.getStatus(), PAYMENT_APPROVE_SUCCESS.getDescription());
    }

    private void updatePaymentStatusToFailed(
        PaymentJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        List<PaymentItemJpaEntity> paymentItems = paymentEvent.getPaymentItems();
        paymentEvent.updatePaymentFailCount();
        updateStatus(paymentItems, command.getStatus(), command.getFailure().message());
    }

    private void updatePaymentStatusToUnknown(
        PaymentJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        List<PaymentItemJpaEntity> paymentItems = paymentEvent.getPaymentItems();
        updateStatus(paymentItems, command.getStatus(), PAYMENT_APPROVE_UNKNOWN.getDescription());
    }

    @Override
    public void updateBenefitApplied(String orderId) {
        PaymentJpaEntity payment = paymentJpaRepository.findByOrderId(orderId);
        payment.applyBenefit();
    }

}
