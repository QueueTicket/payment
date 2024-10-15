package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderJpaEntity, Long> {

    List<PaymentOrderJpaEntity> findByOrderId(String orderId);

    @Query("SELECT SUM(p.amount) FROM PaymentOrderJpaEntity p WHERE p.orderId = :orderId")
    BigDecimal findTotalAmountByOrderId(@Param("orderId") String orderId);

}
