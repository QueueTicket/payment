package com.qticket.payment.adapter.out.persistnece.repository;

import com.qticket.payment.adapter.out.persistnece.entity.PaymentEventJpaEntity;
import com.qticket.payment.domain.payment.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepository implements PaymentRepository {

    private final PaymentEventJpaRepository paymentEventJpaRepository;
    private final PaymentOrderJpaRepository paymentOrderJpaRepository;

    @Override
    @Transactional
    public void save(PaymentEvent paymentEvent) {
        PaymentEventJpaEntity paymentEventJpaEntity = paymentEvent.toEntity();
        paymentEventJpaRepository.save(paymentEventJpaEntity);
        paymentOrderJpaRepository.saveAll(paymentEventJpaEntity.getPaymentOrders());
    }

}
