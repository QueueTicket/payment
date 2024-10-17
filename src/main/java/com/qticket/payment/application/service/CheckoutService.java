package com.qticket.payment.application.service;

import com.qticket.payment.application.port.in.CheckoutUseCase;
import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.application.port.out.LoadConcertPort;
import com.qticket.payment.application.port.out.LoadCouponPort;
import com.qticket.payment.application.port.out.LoadCustomerPort;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.domain.checkout.CheckoutResult;
import com.qticket.payment.domain.checkout.Coupon;
import com.qticket.payment.domain.checkout.Customer;
import com.qticket.payment.domain.checkout.Reservation;
import com.qticket.payment.domain.payment.PaymentEvent;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService implements CheckoutUseCase {

    private final LoadCustomerPort loadCustomerPort;
    private final LoadCouponPort loadCouponPort;
    private final LoadConcertPort loadConcertPort;
    private final SavePaymentPort savePaymentPort;

    /**
     * TODO 쿠폰 할인 금액 적용
     * <p>
     * - maxDiscountAmount는 discountAmount보다 클 수 없음
     * - DiscountPolicy에 따라서 할인 금액/할인률을 구분
     *
     * @param command
     * @return
     */
    @Override
    public CheckoutResult checkout(CheckoutCommand command) {
        Customer customer = loadCustomerPort.getCustomer(command.customerId());
        Reservation reservation = loadConcertPort.getTicket(command.customerId(), command.concertId());
        Coupon coupon = loadCouponPort.getCoupon(command.couponId(), reservation);

        PaymentEvent paymentEvent = createPaymentEvent(command, customer, reservation, coupon);
        savePaymentPort.save(paymentEvent);

        return CheckoutResult.of(paymentEvent);
    }


    private PaymentEvent createPaymentEvent(
        CheckoutCommand command,
        Customer customer,
        Reservation reservation,
        Coupon coupon
    ) {
        return PaymentEvent.of(
            customer.id(),
            command.idempotencyKey(),
            reservation.seatNames(),
            coupon.id(),
            coupon.applicableDiscountAmount(reservation.totalPrice()),
            PaymentMethod.EASY_PAY,
            PaymentOrder.preOrder(
                command.idempotencyKey(),
                reservation
            )
        );
    }


}
