package com.qticket.payment.adapter.out.persistnece.repository;

public interface PaymentValidationRepository {

    void isValid(String orderId, Long amount);

}
