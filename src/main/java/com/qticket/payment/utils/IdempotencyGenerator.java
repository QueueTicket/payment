package com.qticket.payment.utils;

import com.qticket.payment.adapter.in.web.view.request.CheckoutRequest;
import java.util.UUID;

public class IdempotencyGenerator {

    public static String generate(CheckoutRequest checkoutRequest) {
        return UUID.nameUUIDFromBytes(checkoutRequest.toString().getBytes()).toString();
    }

}
