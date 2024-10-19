package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentItemHistoryJpaRepository extends JpaRepository<PaymentItemHistoryJpaEntity, Long> {

}
