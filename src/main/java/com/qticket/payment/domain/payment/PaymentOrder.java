package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaEntity;
import com.qticket.payment.domain.checkout.Reservation;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

// TODO 정산을 위한 판매자 정보 추가 : sellerId
// TODO 일급 컬렉션으로 도메인 로직 이관
@Builder
public record PaymentOrder(
    Long id,
    Long paymentEventId,
    Long customerId,
    String orderId,
    String concertId,
    String seatId,
    BigDecimal amount,
    PaymentStatus status,
    boolean isLedgerCompleted,
    boolean isSettlementCompleted
) {

    private PaymentOrder(
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

    public static List<PaymentOrder> preOrder(
        String orderId,
        Reservation reservation
    ) {
        return reservation.concertSeats()
            .stream()
            .map(it -> of(orderId, reservation.concertId(), it.id(), it.price()))
            .toList();
    }

    public static PaymentOrder of(
        String orderId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        return new PaymentOrder(
            orderId,
            concertId,
            seatId,
            amount
        );
    }

    public PaymentOrderJpaEntity toEntity() {
        return PaymentOrderJpaEntity.of(
            orderId,
            concertId,
            seatId,
            amount
        );
    }

}
