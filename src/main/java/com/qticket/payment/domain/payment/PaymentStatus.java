package com.qticket.payment.domain.payment;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("결제 전"),
    PROCESSING("결제 중"),
    SUCCESS("결제 성공"),
    FAILED("결제 실패"),
    UNKNOWN_APPROVE("결제 승인 미 확인"),
    ;

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public boolean isChangeableInProcessing() {
        return !isNotChangeableInProcessing();
    }

    public boolean isNotChangeableInProcessing() {
        return this == SUCCESS || this == FAILED;
    }

}
