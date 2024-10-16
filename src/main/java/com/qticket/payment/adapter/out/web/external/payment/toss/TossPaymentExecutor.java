package com.qticket.payment.adapter.out.web.external.payment.toss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qticket.payment.adapter.out.web.external.payment.executor.PaymentExecutor;
import com.qticket.payment.adapter.out.web.external.payment.toss.confirm.TossPaymentConfirmResponse;
import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.domain.confirm.ConfirmStatus;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import com.qticket.payment.domain.confirm.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.payment.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TossPaymentExecutor implements PaymentExecutor {

    private final WebClient tossPaymentsWebClient;
    private final ObjectMapper objectMapper;
    private final String CONFIRM_URI = "/v1/payments/confirm";
    private final String IDEMPOTENCY_KEY_HEADER_KEY = "idempotency-Key";

    @Override
    public Mono<PaymentExecutionResult> execute(PaymentConfirmCommand command) {
        try {
            return tossPaymentsWebClient.post()
                .uri(CONFIRM_URI)
                .header(IDEMPOTENCY_KEY_HEADER_KEY, command.orderId())
                .bodyValue(objectMapper.writeValueAsBytes(command))
                .retrieve()
                .bodyToMono(TossPaymentConfirmResponse.class)
                .map(this::mapTo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private PaymentExecutionResult mapTo(TossPaymentConfirmResponse response) {
        return PaymentExecutionResult.of(
            response.paymentKey(),
            response.orderId(),
            ConfirmStatus.valueOf(response.status()),
            new ApproveDetails(
                response.orderName(),
                BigDecimal.valueOf(response.totalAmount()),
                PaymentMethod.valueOfDescription(response.method()),
                LocalDateTime.parse(response.approvedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            ),
            response.failure()
        );
    }

}
