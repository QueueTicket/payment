package com.qticket.payment.domain.approve;

import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PaymentExecutionResult {

    private String paymentKey;
    private String orderId;
    private PaymentStatus status;
    private ApproveDetails confirmDetails;
    private Failure failure;

    @Builder
    public PaymentExecutionResult(
        String paymentKey,
        String orderId,
        PaymentStatus status,
        ApproveDetails confirmDetails,
        Failure failure
    ) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.status = status;
        this.confirmDetails = confirmDetails;
        this.failure = failure;
    }

    public record ApproveDetails(
        String orderName,
        BigDecimal totalAmount,
        PaymentMethod method,
        LocalDateTime approvedAt
    ) {

    }

}
