package com.qticket.payment.application.port.in;

import com.qticket.payment.application.port.in.command.CheckoutCommand;
import com.qticket.payment.domain.checkout.CheckoutResult;

public interface CheckoutUseCase {

    CheckoutResult checkout(CheckoutCommand checkoutCommand);

}
