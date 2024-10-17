package com.qticket.payment.global.config.webclient.base;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class ResponseLogFilter {

    private static final Logger log = LoggerFactory.getLogger(BaseWebClientConfig.class);

    public static ExchangeFilterFunction logResponseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("========== RESPONSE ==========");
            clientResponse.headers()
                .asHttpHeaders()
                .forEach((name, values) -> values.forEach(value -> log.info("{} : {}", name, value)));

            return clientResponse.bodyToMono(DataBuffer.class)
                .flatMap(dataBuffer -> {
                    String body = StandardCharsets.UTF_8.decode(dataBuffer.toByteBuffer()).toString();
                    log.info("Response Body: {}", body);

                    DataBufferUtils.release(dataBuffer);
                    return Mono.just(ClientResponse.from(clientResponse)
                        .body(body)
                        .build());
                });
        });
    }

}
