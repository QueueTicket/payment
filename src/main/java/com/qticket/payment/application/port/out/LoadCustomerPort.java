package com.qticket.payment.application.port.out;

import com.qticket.payment.domain.Customer;

public interface LoadCustomerPort {

    Customer getCustomer(Long customerId);

}
