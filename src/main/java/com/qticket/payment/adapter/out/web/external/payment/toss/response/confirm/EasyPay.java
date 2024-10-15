package com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm;

public record EasyPay(
    String provider,
    double amount,
    double discountAmount,
    String country
) {

}
