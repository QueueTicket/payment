package com.qticket.payment.application.service;

import com.qticket.payment.application.port.in.CheckoutUseCase;
import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.application.port.out.LoadConcertPort;
import com.qticket.payment.application.port.out.LoadCouponPort;
import com.qticket.payment.application.port.out.LoadCustomerPort;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.domain.checkout.CheckoutResult;
import com.qticket.payment.domain.checkout.Concert;
import com.qticket.payment.domain.checkout.Coupon;
import com.qticket.payment.domain.checkout.Customer;
import com.qticket.payment.domain.payment.PaymentEvent;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.domain.payment.PaymentOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService implements CheckoutUseCase {

    private final LoadCustomerPort loadCustomerPort;
    private final LoadConcertPort loadConcertPort;
    private final LoadCouponPort loadCouponPort;
    private final SavePaymentPort savePaymentPort;

    @Override
    public CheckoutResult checkout(CheckoutCommand command) {
        Customer customer = loadCustomerPort.getCustomer(command.customerId());
        Concert concert = loadConcertPort.getConcert(command.concertId());
        Coupon coupon = loadCouponPort.getCoupon(command.couponId());
        PaymentEvent paymentEvent = createPaymentEvent(command, customer, concert, coupon);

        savePaymentPort.save(paymentEvent);

        return CheckoutResult.of(paymentEvent);
    }

    private PaymentEvent createPaymentEvent(
        CheckoutCommand command,
        Customer customer,
        Concert concert,
        Coupon coupon
    ) {
        return PaymentEvent.of(
            customer.id(),
            command.idempotencyKey(),
            concert.seatNames(),
            PaymentMethod.EASY_PAY,
            PaymentOrder.preOrder(
                command.idempotencyKey(),
                concert,
                coupon
            )
        );
    }

}
