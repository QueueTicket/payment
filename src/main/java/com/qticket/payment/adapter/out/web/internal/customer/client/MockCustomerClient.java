package com.qticket.payment.adapter.out.web.internal.customer.client;

import com.qticket.payment.domain.checkout.Customer;
import org.springframework.stereotype.Component;

@Component
public class MockCustomerClient implements CustomerClient {

    @Override
    public Customer getCustomer(Long customerId) {
        return new Customer(1L, "홍길동", "test@gmail.com", "010-1111-2222");
    }

}
