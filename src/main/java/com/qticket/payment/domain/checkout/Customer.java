package com.qticket.payment.domain.checkout;

public record Customer(
    Long id,
    String name,
    String email,
    String phone
) {

}
