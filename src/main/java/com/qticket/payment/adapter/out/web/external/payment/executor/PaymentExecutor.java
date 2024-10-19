package com.qticket.payment.adapter.out.web.external.payment.executor;

import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.domain.approve.PaymentExecutionResult;
import reactor.core.publisher.Mono;

public interface PaymentExecutor {

    Mono<PaymentExecutionResult> execute(PaymentApproveCommand command);

}
