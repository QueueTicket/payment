package com.qticket.payment.application.port.in;

import com.qticket.payment.domain.CheckoutResult;

public interface CheckoutUseCase {

    CheckoutResult checkout(CheckoutCommand checkoutCommand);

}
