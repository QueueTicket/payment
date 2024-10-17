package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaEntity;
import com.qticket.payment.domain.checkout.Coupon;
import com.qticket.payment.domain.checkout.Reservation;
import java.math.BigDecimal;
import java.util.List;

// TODO 정산을 위한 판매자 정보 : sellerId
public record PaymentOrder(
    Long id,
    Long paymentEventId,
    Long customerId,
    String orderId,
    String concertId,
    String seatId,
    String couponId,
    BigDecimal amount,
    PaymentStatus status,
    boolean isLedgerCompleted,
    boolean isSettlementCompleted
) {

    private PaymentOrder(
        String orderId,
        String concertId,
        String seatId,
        String couponId,
        BigDecimal amount
    ) {
        this(
            0L,
            0L,
            0L,
            orderId,
            concertId,
            seatId,
            couponId,
            amount,
            PaymentStatus.PENDING,
            false,
            false
        );
    }

    public static List<PaymentOrder> preOrder(
        String orderId,
        Reservation reservation,
        Coupon coupon
    ) {
        return reservation.concertSeats()
            .stream()
            .map(it -> of(orderId, reservation.concertId(), it.id(), coupon.id(), it.price()))
            .toList();
    }

    public static PaymentOrder of(
        String orderId,
        String concertId,
        String seatId,
        String couponId,
        BigDecimal amount
    ) {
        return new PaymentOrder(
            orderId,
            concertId,
            seatId,
            couponId,
            amount
        );
    }

    public PaymentOrderJpaEntity toEntity() {
        return PaymentOrderJpaEntity.of(
            orderId,
            concertId,
            seatId,
            couponId,
            amount
        );
    }

}
