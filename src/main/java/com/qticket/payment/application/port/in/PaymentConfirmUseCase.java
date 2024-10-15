package com.qticket.payment.application.port.in;

import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.domain.confirm.PaymentConfirmResult;
import reactor.core.publisher.Mono;

public interface PaymentConfirmUseCase {

    Mono<PaymentConfirmResult> confirm(PaymentConfirmCommand command);

}
