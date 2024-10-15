package com.qticket.payment.adapter.out.web.internal.customer;

import com.qticket.payment.adapter.out.web.internal.customer.client.CustomerClient;
import com.qticket.payment.application.port.out.LoadCustomerPort;
import com.qticket.payment.domain.checkout.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerWebAdapter implements LoadCustomerPort {

    private final CustomerClient customerClient;

    @Override
    public Customer getCustomer(Long customerId) {
        return customerClient.getCustomer(customerId);
    }

}
