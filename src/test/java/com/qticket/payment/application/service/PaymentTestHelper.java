package com.qticket.payment.application.service;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentEventJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderHistoryJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentOrderJpaRepository;
import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.config.base.SpringBootTestBase;
import com.qticket.payment.config.p6spy.P6spySqlFormatConfig;
import com.qticket.payment.domain.payment.PaymentMethod;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(P6spySqlFormatConfig.class)
public class PaymentTestHelper extends SpringBootTestBase {

    protected final String orderId = UUID.randomUUID().toString();
    protected final CheckoutCommand checkoutCommand = new CheckoutCommand(
        1L,
        "test-coupon-Id",
        "test-concertId",
        List.of("test-seat-a"),
        PaymentMethod.EASY_PAY,
        orderId
    );

    @Autowired
    protected PaymentEventJpaRepository paymentEventJpaRepository;

    @Autowired
    protected PaymentOrderJpaRepository paymentOrderJpaRepository;

    @Autowired
    protected PaymentOrderHistoryJpaRepository paymentOrderHistoryJpaRepository;

    @BeforeEach
    void tearDown() {
        paymentOrderHistoryJpaRepository.deleteAll();
        paymentOrderJpaRepository.deleteAll();
        paymentEventJpaRepository.deleteAll();
    }

}
