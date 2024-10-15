package com.qticket.payment.adapter.out.persistnece.repository;

import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;

public interface PaymentStatusUpdateRepository {

    void updatePaymentOrderStatusToProcessing(String orderId, String paymentKey);

    void updatePaymentOrderStatus(PaymentStatusUpdateCommand command);

}
