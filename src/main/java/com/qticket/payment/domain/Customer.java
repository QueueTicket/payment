package com.qticket.payment.domain;

public record Customer(
    Long id,
    String name,
    String email,
    String phone
) {

}
