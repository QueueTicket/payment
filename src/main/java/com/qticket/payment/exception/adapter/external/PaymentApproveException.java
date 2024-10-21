package com.qticket.payment.exception.adapter.external;

import com.qticket.payment.adapter.out.web.external.payment.toss.response.error.TossPaymentsErrorCode;
import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;
import lombok.Getter;

@Getter
public class PaymentApproveException extends ApplicationException {

    private TossPaymentsErrorCode errorCode;
    private boolean isRetryableError;
    private PaymentStatus paymentStatus;
    private Throwable cause;

    public PaymentApproveException(TossPaymentsErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
        this.errorCode = errorCode;
        this.isRetryableError = errorCode.isRetryableError();
        this.paymentStatus = errorCode.getType();
    }

    public PaymentApproveException(PaymentErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
        this.cause = cause;
        this.isRetryableError = true;
        this.paymentStatus = PaymentStatus.UNKNOWN_APPROVE;
    }

}
