package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.domain.payment.PaymentStatus;
import com.qticket.payment.exception.persistence.AlreadyTerminatedPayment;
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
@Table(name = "payment_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "payment_id",
        foreignKey = @ForeignKey(name = "FK_ITEM_TO_PAYMENT")
    )
    private PaymentJpaEntity payment;

    private String orderId;
    private String concertId;
    private String seatId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;
    private final boolean isLedgerCompleted = false;
    private final boolean isSettlementCompleted = false;

    private PaymentItemJpaEntity(
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

    public static PaymentItemJpaEntity of(
        String orderId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        return new PaymentItemJpaEntity(orderId, concertId, seatId, amount);
    }

    public void toPaymentEvent(PaymentJpaEntity paymentJpaEntity) {
        payment = paymentJpaEntity;
    }

    public boolean isChangeableInProcessing() {
        return status.isChangeableInProcessing();
    }

    public void checkIsChangeableInProcessing() {
        if (status.isNotChangeableInProcessing()) {
            throw new AlreadyTerminatedPayment(orderId, status);
        }
    }

    public void updateStatus(PaymentStatus newStatus) {
        status = newStatus;
    }

}
