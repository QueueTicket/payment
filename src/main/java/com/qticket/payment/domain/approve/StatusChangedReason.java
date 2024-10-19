package com.qticket.payment.domain.approve;

import lombok.Getter;

@Getter
public enum StatusChangedReason {

    PAYMENT_APPROVE_STARTED("PAYMENT_APPROVE_STARTED"),

    PAYMENT_APPROVE_SUCCESS("PAYMENT_APPROVE_SUCCESS"),

    PAYMENT_APPROVE_UNKNOWN("PAYMENT_APPROVE_UNKNOWN"),
    ;

    private final String description;

    StatusChangedReason(String description) {
        this.description = description;
    }
}
