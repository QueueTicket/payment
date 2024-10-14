package com.qticket.payment.adapter.out.web.customer.client;

import com.qticket.payment.domain.Customer;

public interface CustomerClient {

    Customer getCustomer(Long customerId);

}
