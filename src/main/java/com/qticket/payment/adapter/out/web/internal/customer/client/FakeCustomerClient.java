package com.qticket.payment.adapter.out.web.internal.customer.client;

import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class FakeCustomerClient implements CustomerMockClient {

    @Override
    public CustomerResponse getCustomer(Long customerId) {
        return new CustomerResponse(
            customerId,
            "test@gmail.com",
            "홍길동",
            "010-1111-2222",
            "CUSTOMER",
            LocalDateTime.of(2012, 12, 28, 9, 10, 10)
        );
    }

}
