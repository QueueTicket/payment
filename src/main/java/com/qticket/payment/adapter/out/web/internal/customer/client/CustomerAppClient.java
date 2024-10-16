package com.qticket.payment.adapter.out.web.internal.customer.client;

import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${internal.instance.name.customer}", url = "${wiremock.base.url}")
public interface CustomerAppClient {

    @GetMapping("/user/{userId}")
    CustomerResponse getCustomer(@PathVariable("userId") Long userId);

}
