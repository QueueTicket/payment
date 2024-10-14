package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.payment.PaymentEvent;

public interface SavePaymentPort {

    void save(PaymentEvent paymentEvent);

}
