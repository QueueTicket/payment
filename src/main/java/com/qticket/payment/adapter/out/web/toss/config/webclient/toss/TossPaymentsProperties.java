package com.qticket.payment.adapter.out.web.toss.config.webclient.toss;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pg.toss")
public record TossPaymentsProperties(
    String secretKey,
    String endpoint
) {

}
