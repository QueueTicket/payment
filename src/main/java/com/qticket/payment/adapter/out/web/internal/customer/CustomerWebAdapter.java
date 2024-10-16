package com.qticket.payment.adapter.out.web.internal.customer;

import com.qticket.payment.adapter.out.web.internal.customer.client.CustomerAppClient;
import com.qticket.payment.adapter.out.web.internal.customer.client.CustomerMockClient;
import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;
import com.qticket.payment.application.port.out.LoadCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerWebAdapter implements LoadCustomerPort {

    private final CustomerAppClient customerAppClient;
    private final CustomerMockClient customerMockClient;

    @Value("${feature.toggle.use.internal.customer-app:false}")
    private boolean useInternalClient;

    @Override
    public CustomerResponse getCustomer(Long customerId) {
        if (useInternalClient) {
            return customerAppClient.getCustomer(customerId);
        }
        return customerMockClient.getCustomer(customerId);
    }

}
