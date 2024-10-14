package com.qticket.payment.adapter.out.web.toss.config.webclient.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class RequestLogFilter {

    private static final Logger log = LoggerFactory.getLogger(BaseWebClientConfig.class);

    public static ExchangeFilterFunction addCustomHeaderFilter() {
        return (req, next) -> next.exchange(ClientRequest.from(req)
            .header("from", "payment.qticket.com")
            .build()
        );
    }

    public static ExchangeFilterFunction logRequestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.debug("========== REQUEST ==========");
            log.debug("{} {}", clientRequest.method(), clientRequest.url());
            log.debug("Header : {}", clientRequest.headers());
            return Mono.just(clientRequest);
        });
    }

}
