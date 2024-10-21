package com.qticket.payment.application.port.out;

import reactor.core.publisher.Mono;

public interface AppliedBenefitPort {

    Mono<Void> updateBenefitApplied(String orderId);

}
