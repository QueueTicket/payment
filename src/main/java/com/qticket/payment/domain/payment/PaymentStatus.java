package com.qticket.payment.domain.payment;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("결제 전"),
    PROCESSING("결제 중"),
    COMPLETE("결제 완료"),
    FAILED("결제 실패"),
    UNKNOWN_APPROVE("결제 승인 미 확인"),
    ;

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    private static final String PAYMENT_STATUS_NOT_MATCHED_TO_DESCRIPTION = "요청에 해당하는 결제 상태가 존재하지 않습니다.";

    public static PaymentStatus valueOfDescription(String description) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(description))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(PAYMENT_STATUS_NOT_MATCHED_TO_DESCRIPTION));
    }
}
