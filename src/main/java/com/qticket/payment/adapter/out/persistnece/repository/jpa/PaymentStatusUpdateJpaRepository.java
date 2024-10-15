package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import com.qticket.payment.adapter.out.persistnece.repository.PaymentStatusUpdateRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderHistoryJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderHistoryJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaEntity;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.application.port.out.command.exception.InValidPaymentStatusException;
import com.qticket.payment.domain.payment.PaymentStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentStatusUpdateJpaRepository implements PaymentStatusUpdateRepository {

    private final PaymentEventJpaRepository paymentEventJpaRepository;
    private final PaymentOrderHistoryJpaRepository paymentOrderHistoryJpaRepository;

    private final String REASON_TO_PAYMENT_CONFIRM_STARTED = "PAYMENT_CONFIRM_STARTED";
    private final String REASON_TO_PAYMENT_CONFIRM_SUCCESS = "PAYMENT_CONFIRM_SUCCESS";
    private final String REASON_TO_PAYMENT_CONFIRM_UNKNOWN = "PAYMENT_CONFIRM_UNKNOWN";

    @Override
    public void updatePaymentOrderStatusToProcessing(String orderId, String paymentKey) {
        PaymentEventJpaEntity paymentEvent = paymentEventJpaRepository.findByOrderId(orderId);
        List<PaymentOrderJpaEntity> paymentOrders = paymentEvent.extractChangeableProcessingOrders();

        checkIsChangeableInProcessingPaymentOrder(paymentOrders);
        registerPaymentKey(paymentEvent, paymentKey);
        updateStatus(paymentOrders, PaymentStatus.PROCESSING);
        saveChangePaymentStatusHistory(paymentOrders, PaymentStatus.PROCESSING, REASON_TO_PAYMENT_CONFIRM_STARTED);
    }

    private void registerPaymentKey(PaymentEventJpaEntity paymentEvent, String paymentKey) {
        paymentEvent.registerPaymentKey(paymentKey);
    }

    private void checkIsChangeableInProcessingPaymentOrder(List<PaymentOrderJpaEntity> paymentOrders) {
        paymentOrders.forEach(PaymentOrderJpaEntity::checkIsChangeableInProcessing);
    }

    public void saveChangePaymentStatusHistory(
        List<PaymentOrderJpaEntity> paymentOrders,
        PaymentStatus updatedStatus,
        String reason
    ) {
        paymentOrderHistoryJpaRepository.saveAll(paymentOrders.stream()
            .map(it -> PaymentOrderHistoryJpaEntity.of(it, updatedStatus, reason))
            .toList()
        );
    }

    public void updateStatus(List<PaymentOrderJpaEntity> paymentOrders, PaymentStatus Processing) {
        paymentOrders.forEach(it -> it.updateStatus(Processing));
    }

    @Override
    public void updatePaymentOrderStatus(PaymentStatusUpdateCommand command) {
        PaymentEventJpaEntity paymentEvent = paymentEventJpaRepository.findByOrderId(command.getOrderId());

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
        PaymentEventJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        List<PaymentOrderJpaEntity> paymentOrders = paymentEvent.getPaymentOrders();

        paymentEvent.updatePaymentDetails(command.getApproveDetails());
        updateStatus(paymentOrders, command.getStatus());
        saveChangePaymentStatusHistory(paymentOrders, command.getStatus(), REASON_TO_PAYMENT_CONFIRM_SUCCESS);
    }

    private void updatePaymentStatusToFailed(
        PaymentEventJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        List<PaymentOrderJpaEntity> paymentOrders = paymentEvent.getPaymentOrders();

        paymentEvent.updatePaymentFailCount();
        updateStatus(paymentOrders, command.getStatus());
        saveChangePaymentStatusHistory(paymentOrders, command.getStatus(), command.getFailure().message());
    }

    private void updatePaymentStatusToUnknown(
        PaymentEventJpaEntity paymentEvent,
        PaymentStatusUpdateCommand command
    ) {
        List<PaymentOrderJpaEntity> paymentOrders = paymentEvent.getPaymentOrders();
        updateStatus(paymentOrders, command.getStatus());
        saveChangePaymentStatusHistory(paymentOrders, command.getStatus(), REASON_TO_PAYMENT_CONFIRM_UNKNOWN);
    }

}
