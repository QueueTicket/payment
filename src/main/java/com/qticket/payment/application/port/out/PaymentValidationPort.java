package com.qticket.payment.application.port.out;

public interface PaymentValidationPort {

    void isValid(String orderId, Long amount);

}
