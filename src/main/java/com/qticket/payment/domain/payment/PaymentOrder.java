package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.entity.PaymentOrderJpaEntity;
import com.qticket.payment.domain.Concert;
import com.qticket.payment.domain.Coupon;
import java.math.BigDecimal;
import java.util.List;

// TODO 정산을 위한 판매자 정보 : sellerId
public record PaymentOrder(
    Long id,
    Long paymentEventId,
    Long customerId,
    String orderId,
    String couponId,
    String concertId,
    String seatId,
    BigDecimal amount,
    PaymentStatus status,
    boolean isLedgerCompleted,
    boolean isSettlementCompleted
) {

    private PaymentOrder(
        String orderId,
        String couponId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        this(
            0L,
            0L,
            0L,
            orderId,
            couponId,
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
        Concert concert,
        Coupon coupon
    ) {
        return concert.concertSeats().stream()
            .map(it -> of(orderId, coupon.id(), concert.id(), it.id(), it.price()))
            .toList();
    }

    public static PaymentOrder of(
        String orderId,
        String couponId,
        String concertId,
        String seatId,
        BigDecimal amount
    ) {
        return new PaymentOrder(
            orderId,
            couponId,
            concertId,
            seatId,
            amount
        );
    }

    public PaymentOrderJpaEntity toEntity() {
        return PaymentOrderJpaEntity.of(
            orderId,
            couponId,
            concertId,
            seatId,
            amount
        );
    }

}
