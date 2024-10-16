package com.qticket.payment.adapter.out.web.external.payment.toss.confirm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// TODO PG 응답 원문은 mongo로 관리
@JsonIgnoreProperties(ignoreUnknown = true)
public record TossPaymentConfirmResponse(
    String paymentKey,
    String type,
    String orderId,
    String orderName,
    String method,
    double totalAmount,
    double balanceAmount,
    String status,
    String requestedAt,
    String approvedAt,
    String lastTransactionKey,
    List<Cancel> cancels,
    EasyPay easyPay,
    Metadata metadata,
    Failure failure
) {

}
