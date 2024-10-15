package com.qticket.payment.adapter.out.web.external.payment.toss.config.client.toss;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pg.toss")
public record TossPaymentsProperties(
    String secretKey,
    String endpoint
) {

}
