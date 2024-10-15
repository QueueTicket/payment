package com.qticket.payment.domain.checkout;

public record Coupon(
    String id,
    String name,
    boolean isAvailability
) {

}
