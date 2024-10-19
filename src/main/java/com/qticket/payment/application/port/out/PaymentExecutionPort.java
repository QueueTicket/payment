package com.qticket.payment.application.port.out;

import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.domain.approve.PaymentExecutionResult;
import reactor.core.publisher.Mono;

public interface PaymentExecutionPort {

    Mono<PaymentExecutionResult> execute(PaymentApproveCommand command);

}
