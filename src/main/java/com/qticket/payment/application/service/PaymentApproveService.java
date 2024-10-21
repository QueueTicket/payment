package com.qticket.payment.application.service;

import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.application.port.in.PaymentApproveUseCase;
import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.application.port.out.PaymentExecutionPort;
import com.qticket.payment.application.port.out.PaymentStatusUpdatePort;
import com.qticket.payment.application.port.out.PaymentValidationPort;
import com.qticket.payment.application.port.out.command.PaymentStatusUpdateCommand;
import com.qticket.payment.domain.approve.PaymentApproveResult;
import com.qticket.payment.domain.approve.PaymentExecutionResult;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.adapter.external.PaymentApproveException;
import com.qticket.payment.exception.adapter.persistence.InValidAmountException;
import com.qticket.payment.exception.application.InValidPaymentStatusException;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentApproveService implements PaymentApproveUseCase {

    private final PaymentStatusUpdatePort paymentStatusUpdatePort;
    private final PaymentValidationPort paymentValidationPort;
    private final PaymentExecutionPort paymentExecutionPort;

    // PaymentAlreadyProcessedException -> InValidPaymentStatusException : paymentStatusUpdatePort.updatePaymentStatusToApproveProcessing
    // PaymentValidationException -> InValidAmountException : paymentValidationPort.validateApprovalPaymentAmount
    // InValidApprovalStatusException, PaymentApproveException, TimeoutException : paymentExecutionPort.execute(command)
    @Override
    public Mono<PaymentApproveResult> approve(PaymentApproveCommand command) {
        return paymentStatusUpdatePort.updatePaymentStatusToApproveProcessing(command.orderId(), command.paymentKey())
            .then(Mono.defer(() -> paymentValidationPort.validateApprovalAmount(command.orderId(), command.amount())))
            .then(Mono.defer(() -> paymentExecutionPort.execute(command)))
            .flatMap(result -> paymentStatusUpdatePort.updatePaymentStatus(PaymentStatusUpdateCommand.from(result))
                .thenReturn(result)
                .map(PaymentApproveResult::of)
                .onErrorResume(it2 -> handlePaymentError(it2, command).map(PaymentApproveResult::of))
            );
    }

    // TODO Chain of Responsibility 패턴을 적용한 코드 리팩토링
    private Mono<? extends PaymentExecutionResult> handlePaymentError(Throwable error, PaymentApproveCommand command) {
        PaymentStatus status = PaymentStatus.UNKNOWN_APPROVE;
        var failure = new Failure(error.getClass().getSimpleName(), error.getMessage());

        if (error instanceof PaymentApproveException paymentException) {
            status = paymentException.getPaymentStatus();
            failure = new Failure(paymentException.getCode(), paymentException.getMessage());
        } else if (error instanceof InValidAmountException amountException) {
            status = PaymentStatus.FAILED;
            failure = new Failure(amountException.getClass().getSimpleName(), amountException.getMessage());
        } else if (error instanceof InValidPaymentStatusException paymentStatusException) {
            status = paymentStatusException.getPaymentStatus();
            failure = new Failure(paymentStatusException.getClass().getSimpleName(),
                paymentStatusException.getMessage());
        } else if (error instanceof TimeoutException) {
            failure = new Failure(error.getClass().getSimpleName(), error.getMessage());
        } else {
            failure = new Failure(error.getClass().getSimpleName(), error.getMessage());
        }

        var paymentStatusUpdateCommand = PaymentStatusUpdateCommand.ofError(
            command.paymentKey(),
            command.orderId(),
            status,
            failure
        );

        return paymentStatusUpdatePort.updatePaymentStatus(paymentStatusUpdateCommand)
            .then(Mono.fromCallable(() -> PaymentExecutionResult.builder()
                .paymentKey(command.paymentKey())
                .orderId(command.orderId())
                .status(paymentStatusUpdateCommand.getStatus())
                .failure(paymentStatusUpdateCommand.getFailure())
                .build()));
    }

}
