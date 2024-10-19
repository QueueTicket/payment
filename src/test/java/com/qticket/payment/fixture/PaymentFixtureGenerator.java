package com.qticket.payment.fixture;

import com.qticket.payment.adapter.out.web.internal.concert.client.response.SeatGrade;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.coupon.client.response.DiscountPolicy;
import com.qticket.payment.config.base.TestBase;
import com.qticket.payment.domain.checkout.Benefit;
import com.qticket.payment.domain.checkout.ConcertSeat;
import com.qticket.payment.domain.checkout.Reservation;
import com.qticket.payment.domain.checkout.Tickets;
import com.qticket.payment.domain.payment.PaymentEvent;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentOrder;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PaymentFixtureGenerator extends TestBase {

    protected final Long customerId = 1L;
    protected final String concertId = "아이유 콘서트";
    protected final String couponId = "신규 회원 쿠폰";
    protected final String orderId = UUID.randomUUID().toString();

    protected ConcertSeat concertSeat = new ConcertSeat(
        UUID.randomUUID().toString(),
        SeatGrade.R,
        new BigDecimal(120_000)
    );

    protected Reservation reservation = new Reservation(
        customerId,
        concertId,
        Tickets.of(List.of(concertSeat)),
        concertSeat.price()
    );

    protected CouponValidateResponse couponValidateResponse = new CouponValidateResponse(
        couponId,
        50,
        DiscountPolicy.PERCENTAGE,
        20_000
    );

    protected Benefit benefit = Benefit.of(couponValidateResponse, reservation.totalPrice());

    protected PaymentEvent paymentEvent = PaymentEvent.builder()
        .customerId(customerId)
        .orderId(orderId)
        .orderName(reservation.seatNames())
        .benefit(benefit)
        .method(PaymentMethod.EASY_PAY)
        .paymentOrders(PaymentOrder.preOrder(
            orderId,
            reservation
        ))
        .build();

}
