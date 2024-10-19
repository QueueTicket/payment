package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderJpaEntity, Long> {

    @Query("""
            SELECT 
                CASE WHEN p.isBenefitApplied = true 
                    THEN COALESCE(SUM(o.amount), 0) - COALESCE(MAX(b.discountAmount), 0) 
                    ELSE COALESCE(SUM(o.amount), 0)
                END
            FROM PaymentEventJpaEntity p
            JOIN PaymentOrderJpaEntity o ON p.id = o.paymentEvent.id
            LEFT JOIN BenefitJpaEntity b ON p.id = b.payment.id
            WHERE p.orderId = :orderId
        """)
    BigDecimal findPaymentAmount(@Param("orderId") String orderId);

}
