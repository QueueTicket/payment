package com.qticket.payment.adapter.out.persistnece.repository;

import com.qticket.payment.domain.payment.PaymentEvent;

public interface PaymentRepository {

    public void save(PaymentEvent paymentEvent);

}
