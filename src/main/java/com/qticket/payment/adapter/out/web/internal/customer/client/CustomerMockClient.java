package com.qticket.payment.adapter.out.web.internal.customer.client;

import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;

public interface CustomerMockClient {

    CustomerResponse getCustomer(Long customerId);

}
