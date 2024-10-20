package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentItemJpaEntity;
import com.qticket.payment.domain.checkout.Reservation;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record PaymentItem(
    Long id,
    Long paymentId,
    Long customerId,
    String orderId,
    String concertId,
    String seatId,
    BigDecimal amount,
    PaymentStatus status,
    boolean isLedgerCompleted,
    boolean isSettlementCompleted
) {

    private PaymentItem(
        String orderId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        this(
            0L,
            0L,
            0L,
            orderId,
            concertId,
            seatId,
            amount,
            PaymentStatus.PENDING,
            false,
            false
        );
    }

    public static List<PaymentItem> preOrder(
        String orderId,
        Reservation reservation
    ) {
        return reservation.concertSeats()
            .stream()
            .map(it -> of(orderId, reservation.concertId(), it.id(), it.price()))
            .toList();
    }

    public static PaymentItem of(
        String orderId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        return new PaymentItem(
            orderId,
            concertId,
            seatId,
            amount
        );
    }

    public PaymentItemJpaEntity toEntity() {
        return PaymentItemJpaEntity.of(
            orderId,
            concertId,
            seatId,
            amount
        );
    }

}
