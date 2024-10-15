package com.qticket.payment.application.service;

import com.qticket.payment.application.port.in.PaymentConfirmUseCase;
import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.application.port.out.PaymentExecutionPort;
import com.qticket.payment.application.port.out.PaymentStatusUpdatePort;
import com.qticket.payment.application.port.out.PaymentValidationPort;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.domain.confirm.PaymentConfirmResult;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentConfirmService implements PaymentConfirmUseCase {

    private final PaymentStatusUpdatePort paymentStatusUpdatePort;
    private final PaymentValidationPort paymentValidationPort;
    private final PaymentExecutionPort paymentExecutionPort;

    @Override
    public Mono<PaymentConfirmResult> confirm(PaymentConfirmCommand command) {
        paymentStatusUpdatePort.updateStatusToProcessing(command.orderId(), command.paymentKey());
        paymentValidationPort.isValid(command.orderId(), command.amount());

        // TODO 비동기 + 동기 로직의 blocking 처리 개선 : paymentStatusUpdatePort 반환 처리를 mono 타입으로 변경
        PaymentExecutionResult result = paymentExecutionPort.execute(command).block();
        paymentStatusUpdatePort.updatePaymentStatus(
            PaymentStatusUpdateCommand.from(result)
        );

        return Mono.just(PaymentConfirmResult.of(result));
    }

}
