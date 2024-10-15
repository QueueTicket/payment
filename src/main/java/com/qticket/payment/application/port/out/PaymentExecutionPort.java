package com.qticket.payment.application.port.out;

import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import reactor.core.publisher.Mono;

public interface PaymentExecutionPort {

    Mono<PaymentExecutionResult> execute(PaymentConfirmCommand command);

}
