package com.qticket.payment.domain.checkout;

import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;

public record Customer(
    Long id,
    String name,
    String email,
    String phone
) {

    public static Customer of(CustomerResponse customer) {
        return new Customer(customer.id(), customer.name(), customer.email(), customer.phoneNumber());
    }

}
