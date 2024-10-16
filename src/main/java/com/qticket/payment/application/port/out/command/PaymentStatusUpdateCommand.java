package com.qticket.payment.application.port.out.command;

import com.qticket.payment.adapter.out.web.external.payment.toss.confirm.Failure;
import com.qticket.payment.application.port.out.command.exception.InValidPaymentStatusException;
import com.qticket.payment.application.port.out.command.exception.MissingConfirmCompleteDetailsException;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import com.qticket.payment.domain.confirm.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.payment.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentStatusUpdateCommand {

    private String paymentKey;
    private String orderId;
    private PaymentStatus status;
    private ApproveDetails approveDetails;
    private Failure failure;

    private PaymentStatusUpdateCommand(
        String paymentKey,
        String orderId,
        PaymentStatus status,
        ApproveDetails approveDetails,
        Failure failure
    ) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.status = status;
        this.approveDetails = approveDetails;
        this.failure = failure;
        validateDetailsByStatus();
    }

    private void validateDetailsByStatus() {
        switch (status) {
            case SUCCESS -> {
                if (approveDetails == null) {
                    throw new MissingConfirmCompleteDetailsException(paymentKey, orderId, status);
                }
            }
            case FAILED -> {
                if (failure == null) {
                    throw new MissingConfirmCompleteDetailsException(paymentKey, orderId, status);
                }
            }
            case UNKNOWN_APPROVE -> {

            }
            default -> throw new InValidPaymentStatusException(paymentKey, orderId, status);
        }
    }

    public static PaymentStatusUpdateCommand from(PaymentExecutionResult result) {
        return new PaymentStatusUpdateCommand(
            result.getPaymentKey(),
            result.getOrderId(),
            result.getpaymentStatus(),
            result.getConfirmDetails(),
            result.getFailure()
        );
    }

}
