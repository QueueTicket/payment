package com.qticket.payment.domain.approve;

import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.domain.NotValidConfirmedStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentExecutionResult {

    private String paymentKey;
    private String orderId;
    private ApprovalStatus approvalStatus;
    private ApproveDetails confirmDetails;
    private Failure failure;

    private PaymentExecutionResult(
        String paymentKey,
        String orderId,
        ApprovalStatus approvalStatus,
        ApproveDetails confirmDetails,
        Failure failure
    ) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.approvalStatus = approvalStatus;
        this.confirmDetails = confirmDetails;
        this.failure = failure;
        validateStatus();
    }

    public static PaymentExecutionResult of(
        String paymentKey,
        String orderId,
        ApprovalStatus approvalStatus,
        ApproveDetails approveDetails,
        Failure failure
    ) {
        return new PaymentExecutionResult(
            paymentKey,
            orderId,
            approvalStatus,
            approveDetails,
            failure
        );
    }

    private void validateStatus() {
        if (!approvalStatus.isValidConfirmCompletedStatus()) {
            throw new NotValidConfirmedStatusException(paymentKey, orderId, approvalStatus);
        }
    }

    public PaymentStatus getpaymentStatus() {
        return approvalStatus.toPaymentStatus();
    }

    public record ApproveDetails(
        String orderName,
        BigDecimal totalAmount,
        PaymentMethod method,
        LocalDateTime approvedAt
    ) {

    }

}
