package com.qticket.payment.domain;

public record Coupon(
    String id,
    String name,
    boolean isAvailability
) {

}
