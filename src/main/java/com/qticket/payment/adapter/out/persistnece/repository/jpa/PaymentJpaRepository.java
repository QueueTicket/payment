package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import com.qticket.payment.adapter.out.persistnece.repository.PaymentRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaEntity;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaRepository;
import com.qticket.payment.domain.payment.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentJpaRepository implements PaymentRepository {

    private final PaymentEventJpaRepository paymentEventJpaRepository;
    private final PaymentOrderJpaRepository paymentOrderJpaRepository;

    @Override
    public void save(PaymentEvent paymentEvent) {
        PaymentEventJpaEntity paymentEventJpaEntity = paymentEvent.toEntity();
        paymentEventJpaRepository.save(paymentEventJpaEntity);
        paymentOrderJpaRepository.saveAll(paymentEventJpaEntity.getPaymentOrders());
    }

    @Override
    public void updateBenefitApplied(String orderId) {
        PaymentEventJpaEntity payment = paymentEventJpaRepository.findByOrderId(orderId);
        payment.applyBenefit();
    }

}
