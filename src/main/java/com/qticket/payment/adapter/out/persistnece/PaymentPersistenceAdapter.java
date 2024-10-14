package com.qticket.payment.adapter.out.persistnece;

import com.qticket.payment.adapter.out.persistnece.repository.PaymentRepository;
import com.qticket.payment.application.port.out.SavePaymentPort;
import com.qticket.payment.domain.payment.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements SavePaymentPort {

    private final PaymentRepository paymentRepository;

    @Override
    public void save(PaymentEvent paymentEvent) {
        paymentRepository.save(paymentEvent);
    }

}
