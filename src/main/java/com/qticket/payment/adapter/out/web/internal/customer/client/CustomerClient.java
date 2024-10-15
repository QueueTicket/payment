package com.qticket.payment.adapter.out.web.internal.customer.client;

import com.qticket.payment.domain.checkout.Customer;

public interface CustomerClient {

    Customer getCustomer(Long customerId);

}
