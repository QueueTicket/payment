package com.qticket.payment.application.service;

import com.qticket.payment.adapter.in.web.api.response.PaymentResponse;
import com.qticket.payment.application.port.in.PaymentRetrieveUseCase;
import com.qticket.payment.application.port.out.PaymentRetrievePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentRetrieveService implements PaymentRetrieveUseCase {

    private final PaymentRetrievePort paymentRetrievePort;

    @Override
    public PaymentResponse retrievePayment(String orderId) {
        return paymentRetrievePort.getPaymentByOrderId(orderId);
    }

}
