package com.qticket.payment.exception.domain;

import com.qticket.payment.domain.approve.ApprovalStatus;
import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.global.exception.ApplicationException;

public class InValidApprovalStatusException extends ApplicationException {

    private static final String MESSAGE_FORMAT = "결제 승인 상태가 올바르지 않습니다.[%s, %s] : %s";

    public InValidApprovalStatusException(String paymentKey, String orderId, ApprovalStatus approvalStatus) {
        super(
            PaymentErrorCode.INVALID_APPROVAL_STATUS,
            MESSAGE_FORMAT.formatted(paymentKey, orderId, approvalStatus)
        );
    }

}
