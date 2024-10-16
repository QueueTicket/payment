package com.qticket.payment.application.port.out;

import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;

public interface LoadCustomerPort {

    CustomerResponse getCustomer(Long customerId);

}
