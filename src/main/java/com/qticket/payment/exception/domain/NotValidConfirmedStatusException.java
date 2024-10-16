package com.qticket.payment.exception.domain;

import com.qticket.payment.domain.confirm.ConfirmStatus;

public class NotValidConfirmedStatusException extends RuntimeException {

    private final String messageFormat = "결제 승인 완료 상태가 올바르지 않습니다.[%s, %s] : %s";
    private String message;

    public NotValidConfirmedStatusException(String paymentKey, String orderId, ConfirmStatus confirmStatus) {
        this.message = messageFormat.formatted(paymentKey, orderId, confirmStatus);
    }

}
