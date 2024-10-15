package com.qticket.payment.application.service;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderHistoryJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaRepository;
import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.base.SpringBootTestBase;
import com.qticket.payment.domain.payment.PaymentMethod;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentTestHelper extends SpringBootTestBase {

    @Autowired
    protected PaymentEventJpaRepository paymentEventJpaRepository;

    @Autowired
    protected PaymentOrderJpaRepository paymentOrderJpaRepository;

    @Autowired
    protected PaymentOrderHistoryJpaRepository paymentOrderHistoryJpaRepository;

    protected final String orderId = UUID.randomUUID().toString();

    protected final CheckoutCommand checkoutCommand = new CheckoutCommand(
        1L,
        "consert-id",
        List.of("seat-a"),
        "coupon-id",
        PaymentMethod.EASY_PAY,
        orderId
    );

    @AfterEach
    void tearDown() {
        paymentOrderHistoryJpaRepository.deleteAll();
        paymentOrderJpaRepository.deleteAll();
        paymentEventJpaRepository.deleteAll();
    }

}
