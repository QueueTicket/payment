package com.qticket.payment.application.port.out;

public interface PaymentValidationPort {

    void isValidPaymentAmount(String orderId, Long amount);

}
