package com.qticket.payment.adapter.out.web.external.payment;

import com.qticket.payment.adapter.out.web.external.payment.executor.PaymentExecutor;
import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.application.port.out.PaymentExecutionPort;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentExecutorWebAdaptor implements PaymentExecutionPort {

    private final PaymentExecutor paymentExecutor;

    @Override
    public Mono<PaymentExecutionResult> execute(PaymentConfirmCommand command) {
        return paymentExecutor.execute(command);
    }

}
