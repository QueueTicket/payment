package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEventJpaRepository extends JpaRepository<PaymentEventJpaEntity, Long> {

    @EntityGraph(attributePaths = "paymentOrders")
    PaymentEventJpaEntity findByOrderId(String orderId);

}
