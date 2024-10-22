package com.qticket.payment.application.port.out;

import com.qticket.payment.adapter.in.web.api.response.PaymentResponse;

public interface PaymentRetrievePort {

    PaymentResponse getPaymentByOrderId(String orderId);

}
