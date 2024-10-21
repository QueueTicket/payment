package com.qticket.payment.application.port.out;

import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import reactor.core.publisher.Mono;

public interface PaymentStatusUpdatePort {

    Mono<Void> updatePaymentStatusToApproveProcessing(String orderId, String paymentKey);

    Mono<Void> updatePaymentStatus(PaymentStatusUpdateCommand command);

}
