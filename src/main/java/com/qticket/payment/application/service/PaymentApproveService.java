package com.qticket.payment.application.service;

import com.qticket.payment.application.port.in.PaymentApproveUseCase;
import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.application.port.out.PaymentExecutionPort;
import com.qticket.payment.application.port.out.PaymentStatusUpdatePort;
import com.qticket.payment.application.port.out.PaymentValidationPort;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.domain.approve.PaymentApproveResult;
import com.qticket.payment.domain.approve.PaymentExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentApproveService implements PaymentApproveUseCase {

    private final PaymentStatusUpdatePort paymentStatusUpdatePort;
    private final PaymentValidationPort paymentValidationPort;
    private final PaymentExecutionPort paymentExecutionPort;

    @Override
    public Mono<PaymentApproveResult> approve(PaymentApproveCommand command) {
        paymentStatusUpdatePort.updateStatusToProcessing(command.orderId(), command.paymentKey());
        paymentValidationPort.isValid(command.orderId(), command.amount());

        PaymentExecutionResult result = paymentExecutionPort.execute(command).block();
        paymentStatusUpdatePort.updatePaymentStatus(
            PaymentStatusUpdateCommand.from(result)
        );

        return Mono.just(PaymentApproveResult.of(result));
    }

}
