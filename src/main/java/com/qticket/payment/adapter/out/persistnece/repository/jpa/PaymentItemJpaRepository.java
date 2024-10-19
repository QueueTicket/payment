package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemJpaEntity;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentItemJpaRepository extends JpaRepository<PaymentItemJpaEntity, Long> {

    @Query("""
            SELECT 
                CASE WHEN p.isBenefitApplied = true 
                    THEN COALESCE(SUM(i.amount), 0) - COALESCE(MAX(b.discountAmount), 0) 
                    ELSE COALESCE(SUM(i.amount), 0)
                END
            FROM PaymentJpaEntity p
            JOIN PaymentItemJpaEntity i ON p.id = i.payment.id
            LEFT JOIN BenefitJpaEntity b ON p.id = b.payment.id
            WHERE p.orderId = :orderId
        """)
    BigDecimal findPaymentAmount(@Param("orderId") String orderId);

}
