package com.qticket.payment.adapter.out.web.external.payment.executor;

import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import reactor.core.publisher.Mono;

public interface PaymentExecutor {

    Mono<PaymentExecutionResult> execute(PaymentConfirmCommand command);

}
