package com.qticket.payment.domain.approve;

import com.qticket.payment.domain.payment.PaymentStatus;
import java.util.Map;
import lombok.Getter;

@Getter
public enum ConfirmStatus {
    IN_PROGRESS("인증 완료 후 승인 대기"),
    WAITING_FOR_DEPOSIT("가상 계좌 입금 대기"),
    DONE("결제 승인 완료"),
    CANCELED("승인된 결제 취소"),
    PARTIAL_CANCELED("승인된 결제의 부분 취소"),
    ABORTED("결제 승인 실패"),
    EXPIRED("30분 미입력으로 인한 거래 취소");

    private static final Map<ConfirmStatus, PaymentStatus> statusMap = Map.of(
        DONE, PaymentStatus.SUCCESS,
        ABORTED, PaymentStatus.FAILED
    );
    private final String description;

    ConfirmStatus(String description) {
        this.description = description;
    }

    public boolean isValidConfirmCompletedStatus() {
        return this == DONE || this == ABORTED;
    }

    public PaymentStatus toPaymentStatus() {
        return statusMap.getOrDefault(this, PaymentStatus.UNKNOWN_APPROVE);
    }

}
