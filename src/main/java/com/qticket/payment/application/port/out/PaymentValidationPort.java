package com.qticket.payment.application.port.out;

public interface PaymentValidationPort {

    void validateApprovalPaymentAmount(String orderId, Long amount);

}
