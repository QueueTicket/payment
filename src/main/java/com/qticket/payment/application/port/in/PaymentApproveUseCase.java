package com.qticket.payment.application.port.in;

import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.domain.approve.PaymentApproveResult;
import reactor.core.publisher.Mono;

public interface PaymentApproveUseCase {

    Mono<PaymentApproveResult> approve(PaymentApproveCommand command);

}
