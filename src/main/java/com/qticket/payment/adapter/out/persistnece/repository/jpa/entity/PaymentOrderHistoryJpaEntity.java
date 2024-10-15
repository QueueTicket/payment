package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.payment.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment_order_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentOrderHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "payment_order_id",
        foreignKey = @ForeignKey(name = "FK_HISTORY_TO_PAYMENT_ORDER")
    )
    private PaymentOrderJpaEntity paymentOrder;

    @Enumerated(EnumType.STRING)
    private PaymentStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus newStatus;
    private String reason;

    private PaymentOrderHistoryJpaEntity(
        PaymentOrderJpaEntity paymentOrder,
        PaymentStatus newStatus,
        String reason
    ) {
        this.paymentOrder = paymentOrder;
        this.previousStatus = paymentOrder.getStatus();
        this.newStatus = newStatus;
        this.reason = reason;
    }

    public static PaymentOrderHistoryJpaEntity of(
        PaymentOrderJpaEntity paymentOrder,
        PaymentStatus newStatus,
        String reason

    ) {
        return new PaymentOrderHistoryJpaEntity(paymentOrder, newStatus, reason);
    }

}
