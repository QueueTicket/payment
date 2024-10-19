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

    /**
     * TODO 목적에 따라 port명 변경
     * loadConcertPort -> loadReservationPort
     * loadCouponPort -> loadBenefitPort
     * TODO 쿠폰 요청 없을 경우 제외 처리
     * TODO 예매 확인을 위한 조회부 분리 loadReservation -> load*Port
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
        return PaymentEvent.prepareEasyPayment(
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

}
