package com.qticket.payment.application.service;

import com.qticket.payment.adapter.out.web.internal.coupon.client.response.CouponValidateResponse;
import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;
import com.qticket.payment.application.port.in.CheckoutUseCase;
import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.application.port.out.LoadConcertPort;
import com.qticket.payment.application.port.out.LoadCouponPort;
import com.qticket.payment.application.port.out.LoadCustomerPort;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.domain.checkout.CheckoutResult;
import com.qticket.payment.domain.checkout.Ticket;
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

    @Override
    public CheckoutResult checkout(CheckoutCommand command) {
        CustomerResponse customer = loadCustomerPort.getCustomer(command.customerId());
        Ticket ticket = loadConcertPort.getTicket(command.concertId());
        CouponValidateResponse coupon = loadCouponPort.getCoupon(command.couponId(), ticket);
        PaymentEvent paymentEvent = createPaymentEvent(command, customer, ticket, coupon);

        savePaymentPort.save(paymentEvent);

        return CheckoutResult.of(paymentEvent);
    }

    private PaymentEvent createPaymentEvent(
        CheckoutCommand command,
        CustomerResponse customer,
        Ticket ticket,
        CouponValidateResponse coupon
    ) {
        return PaymentEvent.of(
            customer.id(),
            command.idempotencyKey(),
            ticket.seatNames(),
            PaymentMethod.EASY_PAY,
            PaymentOrder.preOrder(
                command.idempotencyKey(),
                ticket,
                coupon
            )
        );
    }

}
