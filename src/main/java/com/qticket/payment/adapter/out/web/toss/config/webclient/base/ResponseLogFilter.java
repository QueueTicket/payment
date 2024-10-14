package com.qticket.payment.adapter.out.web.toss.config.webclient.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class ResponseLogFilter {

    private static final Logger log = LoggerFactory.getLogger(BaseWebClientConfig.class);

    public static ExchangeFilterFunction logResponseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.debug("========== RESPONSE ==========");
            clientResponse.headers()
                .asHttpHeaders()
                .forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
            return Mono.just(clientResponse);
        });
    }

}
