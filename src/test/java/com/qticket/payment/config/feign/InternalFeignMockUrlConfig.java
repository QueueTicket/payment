package com.qticket.payment.config.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@TestPropertySource(properties = "wiremock.base.url=http://localhost:${wiremock.server.port}")
public class InternalFeignMockUrlConfig {

//    @Value("${wiremock.server.port}")
//    int port;

    @Value("${wiremock.base.url}")
    String endpoint;

    @Bean
    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> requestTemplate.target("http://localhost:" + port);
        return requestTemplate -> requestTemplate.target(endpoint);
    }

}
