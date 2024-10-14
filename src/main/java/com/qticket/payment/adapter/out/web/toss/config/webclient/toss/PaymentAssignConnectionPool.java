package com.qticket.payment.adapter.out.web.toss.config.webclient.toss;

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
            .maxConnections(200) // 최대 커넥션 수
            .pendingAcquireTimeout(Duration.ofMillis(5000)) // 커넥션 풀에서 기다리는 시간
            .pendingAcquireMaxCount(-1) // 대기 중인 요청에 제한 없음
            .maxIdleTime(Duration.ofSeconds(30)) // 커넥션의 최대 유휴 시간
            .build();
    }

}
