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
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentOrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "payment_event_id",
        foreignKey = @ForeignKey(name = "FK_PAYMENT_ORDER_TO_PAYMENT_EVENT")
    )
    private PaymentEventJpaEntity paymentEvent;

    private Long customerId;
    private String orderId;
    private String concertId;
    private String seatId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;
    private boolean isLedgerCompleted = false;
    private boolean isSettlementCompleted = false;

    private PaymentOrderJpaEntity(
        String orderId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        this.orderId = orderId;
        this.concertId = concertId;
        this.seatId = seatId;
        this.amount = amount;
    }

    public static PaymentOrderJpaEntity of(
        String orderId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        return new PaymentOrderJpaEntity(orderId, concertId, seatId, amount);
    }

    public void toPaymentEvent(PaymentEventJpaEntity paymentEventJpaEntity) {
        paymentEvent = paymentEventJpaEntity;
        customerId = paymentEvent.getCustomerId();
    }

    public boolean isChangeableInProcessing() {
        return status.isChangeableInProcessing();
    }

    public void checkIsChangeableInProcessing() {
        status.checkIsChangeableInProcessing();
    }

    public void updateStatus(PaymentStatus newStatus) {
        status = newStatus;
    }

}
