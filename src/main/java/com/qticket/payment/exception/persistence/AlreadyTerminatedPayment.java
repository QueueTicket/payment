package com.qticket.payment.exception.persistence;

import com.qticket.payment.domain.payment.PaymentStatus;

public class AlreadyTerminatedPayment extends RuntimeException {

    private String message;
    private PaymentStatus paymentStatus;

    public AlreadyTerminatedPayment(String message, PaymentStatus paymentStatus) {
        this.message = message;
        this.paymentStatus = paymentStatus;
    }

}
