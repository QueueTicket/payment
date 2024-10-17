package com.qticket.payment.global.config.webclient.toss;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class PaymentAssignConnectionPool {

    public static final String PAYMENT_ASSIGN_CLIENT_POOL = "payment-assign-pool";

    @Bean
    public ConnectionProvider paymentAssignConnectionProvider() {
        return ConnectionProvider.builder(PAYMENT_ASSIGN_CLIENT_POOL)
            .maxConnections(200)
            .pendingAcquireTimeout(Duration.ofMillis(5000))
            .pendingAcquireMaxCount(-1)
            .maxIdleTime(Duration.ofSeconds(30))
            .build();
    }

}
