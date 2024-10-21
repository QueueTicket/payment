package com.qticket.payment.application.service;

import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentItemHistoryJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentItemJpaRepository;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentJpaRepository;
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

    protected final String paymentKey = UUID.randomUUID().toString();
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
    protected PaymentJpaRepository paymentJpaRepository;

    @Autowired
    protected PaymentItemJpaRepository paymentItemJpaRepository;

    @Autowired
    protected PaymentItemHistoryJpaRepository paymentItemHistoryJpaRepository;

    @BeforeEach
    void tearDown() {
        paymentItemHistoryJpaRepository.deleteAll();
        paymentItemJpaRepository.deleteAll();
        paymentJpaRepository.deleteAll();
    }

}
