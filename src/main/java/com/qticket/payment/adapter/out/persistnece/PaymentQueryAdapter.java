package com.qticket.payment.adapter.out.persistnece;

import com.qticket.payment.adapter.in.web.api.response.PaymentResponse;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.PaymentJpaRepository;
import com.qticket.payment.application.port.out.PaymentRetrievePort;
import com.qticket.payment.exception.PaymentErrorCode;
import com.qticket.payment.exception.adapter.persistence.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentQueryAdapter implements PaymentRetrievePort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        return paymentJpaRepository.findPaymentWithItemsAndBenefitByOrderId(orderId)
            .map(PaymentResponse::of)
            .orElseThrow(() -> new PaymentNotFoundException(PaymentErrorCode.PAYMENT_NOT_EXIST, orderId));
    }

}
