package com.qticket.payment.application.port.in;

import com.qticket.payment.adapter.in.web.api.response.PaymentResponse;

public interface PaymentRetrieveUseCase {

    PaymentResponse retrievePayment(String orderId);

}
