package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderJpaEntity, Long> {

    @Query("""
            SELECT COALESCE(SUM(o.amount), 0) - COALESCE(p.discountAmount, 0)
            FROM PaymentEventJpaEntity p
            JOIN PaymentOrderJpaEntity o ON p.id = o.paymentEvent.id
            WHERE p.orderId = :orderId
        """)
    BigDecimal findActualPaymentAmount(@Param("orderId") String orderId);

}
