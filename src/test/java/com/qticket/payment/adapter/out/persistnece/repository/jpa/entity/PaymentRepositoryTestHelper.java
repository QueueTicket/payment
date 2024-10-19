package com.qticket.payment.adapter.out.persistnece.repository.jpa.entity;

import com.qticket.payment.adapter.out.persistnece.repository.PaymentRepository;
import com.qticket.payment.fixture.PaymentFixtureGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public abstract class PaymentRepositoryTestHelper extends PaymentFixtureGenerator {

    @Autowired
    protected PaymentRepository paymentRepository;

    @Autowired
    protected PaymentEventJpaRepository paymentEventJpaRepository;

    @Autowired
    protected PaymentOrderJpaRepository paymentOrderJpaRepository;

}
