package com.qticket.payment.domain.confirm;

import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.application.InValidPaymentStatusException;

public record PaymentConfirmResult(
    PaymentStatus paymentStatus,
    Failure failure,
    String message
) {

    public static PaymentConfirmResult of(
        PaymentExecutionResult result
    ) {
        String message = createMessageByStatus(result);
        return new PaymentConfirmResult(result.getpaymentStatus(), result.getFailure(), message);
    }

    private static String createMessageByStatus(PaymentExecutionResult result) {
        return switch (result.getpaymentStatus()) {
            case SUCCESS -> "결제에 성공하였습니다.";
            case FAILED -> "결제에 실패하였습니다.";
            case UNKNOWN_APPROVE -> "결제 중 알 수 없는 오류가 발생하였습니다.";

            default -> throw new InValidPaymentStatusException(
                result.getPaymentKey(),
                result.getOrderId(),
                result.getpaymentStatus()
            );
        };
    }

}
