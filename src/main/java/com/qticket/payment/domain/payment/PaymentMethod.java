package com.qticket.payment.domain.payment;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD("카드"),
    EASY_PAY("간편결제"),
    TRANSFER("계좌 이체"),
    ;

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public static PaymentMethod valueOfDescription(String description) {
        return Arrays.stream(values())
            .filter(it -> it.description.equals(description))
            .findAny()
            .orElseThrow();
    }
}
