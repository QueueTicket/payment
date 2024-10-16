package com.qticket.payment.domain.payment;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaEntity;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.domain.checkout.Ticket;
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
        Ticket ticket,
        CouponValidateResponse coupon
    ) {
        return ticket.concertSeats()
            .stream()
            .map(it -> of(orderId, coupon.id(), ticket.id(), it.id(), it.price()))
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
