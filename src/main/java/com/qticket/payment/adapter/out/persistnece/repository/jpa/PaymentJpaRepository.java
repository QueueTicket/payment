package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {

    @EntityGraph(attributePaths = "paymentItems.elements")
    PaymentJpaEntity findByOrderId(String orderId);

    @EntityGraph(attributePaths = {"paymentItems.elements", "benefit"})
    Optional<PaymentJpaEntity> findPaymentWithItemsAndBenefitByOrderId(String orderId);

}
