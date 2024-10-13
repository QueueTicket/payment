package com.qticket.payment.adapter.out.web.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qticket.payment.adapter.in.web.api.request.TossPaymentConfirmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TossPaymentExecutor {

    private final WebClient tossPaymentsWebClient;
    private final ObjectMapper objectMapper;
    private final String CONFIRM_URL = "/v1/payments/confirm";

    public Mono<String> execute(TossPaymentConfirmRequest request) {
        try {
            return tossPaymentsWebClient.post()
                .uri(CONFIRM_URL)
                .bodyValue(objectMapper.writeValueAsBytes(request))
                .retrieve()
                .bodyToMono(String.class)
                ;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
