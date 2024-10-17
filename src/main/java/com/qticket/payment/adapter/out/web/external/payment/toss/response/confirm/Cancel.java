package com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm;

import java.util.Optional;

public record Cancel(
    double cancelAmount,
    String cancelReason,
    double taxFreeAmount,
    int taxExemptionAmount,
    double refundableAmount,
    double easyPayDiscountAmount,
    String canceledAt,
    String transactionKey,
    Optional<String> receiptKey,
    String cancelStatus,
    Optional<String> cancelRequestId,
    boolean isPartialCancelable,
    String acquireStatus
) {

}
