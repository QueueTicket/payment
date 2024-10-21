package com.qticket.payment.application.port.out;

import reactor.core.publisher.Mono;

public interface PaymentValidationPort {

    Mono<Void> validateApprovalAmount(String orderId, Long amount);

}
