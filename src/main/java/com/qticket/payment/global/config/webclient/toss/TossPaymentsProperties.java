package com.qticket.payment.global.config.webclient.toss;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pg.toss")
public record TossPaymentsProperties(
    String secretKey,
    String baseUrl,
    String confirmEndpoint
) {

}
