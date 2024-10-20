package com.qticket.payment.application.port.out;

import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;

public interface PaymentStatusUpdatePort {

    void updatePaymentStatusToApproveProcessing(String orderId, String paymentKey);

    void updatePaymentStatus(PaymentStatusUpdateCommand command);

}
