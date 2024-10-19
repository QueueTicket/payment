package com.qticket.payment.adapter.out.persistnece.repository;

import com.qticket.payment.domain.payment.PaymentEvent;

public interface PaymentRepository {

    void save(PaymentEvent paymentEvent);

    void updateBenefitApplied(String orderId);

}
