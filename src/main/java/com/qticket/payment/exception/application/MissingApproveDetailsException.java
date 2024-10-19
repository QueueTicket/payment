package com.qticket.payment.exception.application;

import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;

public class MissingApproveDetailsException extends ApplicationException {

    private static final String MESSAGE_FORMAT = "결제 승인 상세 정보가 누락되었습니다.[%s, %s] : %s";

    public MissingApproveDetailsException(String paymentKey, String orderId, PaymentStatus status) {
        super(
            PaymentErrorCode.ALREADY_TERMINATED_ORDER,
            formattingErrorMessage(MESSAGE_FORMAT, paymentKey, orderId, status)
        );
    }

}
