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

    @Override
    public CheckoutResult checkout(CheckoutCommand command) {
        PaymentEvent paymentEvent = checkoutReservationToPayment(command);
        savePaymentPort.save(paymentEvent);

        return CheckoutResult.of(paymentEvent);
    }

    private PaymentEvent checkoutReservationToPayment(CheckoutCommand command) {
        Customer customer = loadCustomerPort.getCustomer(command.customerId());
        Reservation reservation = loadConcertPort.getTicket(command.customerId(), command.concertId());

        if (command.hasNoCoupon()) {
            return createWithoutCouponPayment(command, customer, reservation);
        }

        Coupon coupon = loadCouponPort.getCoupon(command.couponId(), reservation);
        return createPaymentEvent(command, customer, reservation, coupon);
    }

    private PaymentEvent createPaymentEvent(
        CheckoutCommand command,
        Customer customer,
        Reservation reservation,
        Coupon coupon
    ) {
        return PaymentEvent.preparePayment(
            customer.id(),
            command.idempotencyKey(),
            reservation.seatNames(),
            coupon,
            PaymentOrder.preOrder(
                command.idempotencyKey(),
                reservation
            )
        );
    }

    private PaymentEvent createWithoutCouponPayment(
        CheckoutCommand command,
        Customer customer,
        Reservation reservation
    ) {
        return PaymentEvent.prepareWithoutCouponPayment(
            customer.id(),
            command.idempotencyKey(),
            reservation.seatNames(),
            PaymentOrder.preOrder(
                command.idempotencyKey(),
                reservation
            )
        );
    }

}
