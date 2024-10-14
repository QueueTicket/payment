package com.qticket.payment.adapter.out.persistnece.repository;

import com.qticket.payment.adapter.out.persistnece.entity.PaymentOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderJpaRepository extends JpaRepository<PaymentOrderJpaEntity, Long> {

}
