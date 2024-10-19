package com.qticket.payment.domain.approve;

import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.TossPaymentConfirmResponse;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.domain.NotValidConfirmedStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentExecutionResult {

    private String paymentKey;
    private String orderId;
    private ApproveStatus approveStatus;
    private ApproveDetails confirmDetails;
    private Failure failure;

    private PaymentExecutionResult(
        String paymentKey,
        String orderId,
        ApproveStatus approveStatus,
        ApproveDetails confirmDetails,
        Failure failure
    ) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.approveStatus = approveStatus;
        this.confirmDetails = confirmDetails;
        this.failure = failure;
        validateStatus();
    }

    public static PaymentExecutionResult from(TossPaymentConfirmResponse response) {
        return new PaymentExecutionResult(
            response.paymentKey(),
            response.orderId(),
            ApproveStatus.valueOf(response.status()),
            new ApproveDetails(
                response.orderName(),
                BigDecimal.valueOf(response.totalAmount()),
                PaymentMethod.valueOf(response.method()),
                LocalDateTime.parse(response.approvedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            ),
            response.failure()
        );
    }

    public static PaymentExecutionResult of(
        String paymentKey,
        String orderId,
        ApproveStatus approveStatus,
        ApproveDetails approveDetails,
        Failure failure
    ) {
        return new PaymentExecutionResult(
            paymentKey,
            orderId,
            approveStatus,
            approveDetails,
            failure
        );
    }

    private void validateStatus() {
        if (!approveStatus.isValidConfirmCompletedStatus()) {
            throw new NotValidConfirmedStatusException(paymentKey, orderId, approveStatus);
        }
    }

    public PaymentStatus getpaymentStatus() {
        return approveStatus.toPaymentStatus();
    }

    public record ApproveDetails(
        String orderName,
        BigDecimal totalAmount,
        PaymentMethod method,
        LocalDateTime approvedAt
    ) {

    }

}
