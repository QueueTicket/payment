package com.qticket.payment.domain.approve;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.qticket.payment.exception.domain.InValidApprovalStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:PaymentExecutionResult")
class PaymentExecutionResultTest {

    @Test
    @DisplayName("[예외]결제 승인 상태가 올바르지 않은 경우")
    void throwException_WhenInvalidApprovalStatus() {
        // When & Then
        assertThatExceptionOfType(InValidApprovalStatusException.class)
            .isThrownBy(() -> PaymentExecutionResult.of(
                "payment-key",
                "order-id",
                ApprovalStatus.CANCELED,
                null,
                null
            ));
    }

}
