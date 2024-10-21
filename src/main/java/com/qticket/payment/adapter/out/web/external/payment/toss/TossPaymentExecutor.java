package com.qticket.payment.adapter.out.web.external.payment.toss;

import static com.qticket.payment.exception.PaymentErrorCode.PAYMENT_APPROVED_TIMEOUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qticket.payment.adapter.out.web.external.payment.executor.PaymentExecutor;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.Failure;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.confirm.TossPaymentConfirmResponse;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.error.TossPaymentsErrorCode;
import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.domain.approve.ApprovalStatus;
import com.qticket.payment.domain.approve.PaymentExecutionResult;
import com.qticket.payment.domain.approve.PaymentExecutionResult.ApproveDetails;
import com.qticket.payment.domain.payment.PaymentMethod;
import com.qticket.payment.exception.adapter.external.PaymentApproveException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.Retry.RetrySignal;
import reactor.util.retry.RetryBackoffSpec;

@Slf4j
@Component
public class TossPaymentExecutor implements PaymentExecutor {

    private final WebClient tossPaymentsWebClient;
    private final ObjectMapper objectMapper;
    private final String endpoint;

    public TossPaymentExecutor(
        WebClient tossPaymentsWebClient,
        ObjectMapper objectMapper,
        @Value("${pg.toss.confirm-endpoint}") String endpoint
    ) {
        this.tossPaymentsWebClient = tossPaymentsWebClient;
        this.objectMapper = objectMapper;
        this.endpoint = endpoint;
    }

    private String IDEMPOTENCY_KEY_HEADER_KEY = "idempotency-Key";

    @Override
    public Mono<PaymentExecutionResult> execute(PaymentApproveCommand command) {
        try {
            return tossPaymentsWebClient.post()
                .uri(endpoint)
                .header(IDEMPOTENCY_KEY_HEADER_KEY, command.orderId())
                .bodyValue(objectMapper.writeValueAsBytes(command))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrorResponse)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleErrorResponse)
                .bodyToMono(TossPaymentConfirmResponse.class)
                .map(this::mapTo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<? extends Throwable> handleErrorResponse(ClientResponse errorResponse) {
        Mono<PaymentApproveException> paymentApproveExceptionMono = errorResponse.bodyToMono(Failure.class)
            .flatMap(failure -> {
                TossPaymentsErrorCode error = TossPaymentsErrorCode.valueOf(failure.code());
                return Mono.error(new PaymentApproveException(error));
            });
        return retryError(paymentApproveExceptionMono);
    }

    private static Mono<PaymentApproveException> retryError(Mono<PaymentApproveException> errorMono) {
        return errorMono.retryWhen(Retry.backoff(2, Duration.ofSeconds(1))
            .jitter(0.1)
            .filter(TossPaymentExecutor::isRetryableThrows)
            .doBeforeRetry(TossPaymentExecutor::loggingExecuteRetry)
            .onRetryExhaustedThrow(getRetryExhausted())
        );
    }

    private static void loggingExecuteRetry(RetrySignal retrySignal) {
        PaymentApproveException failure = (PaymentApproveException) retrySignal.failure();
        log.warn("""
                \s
                Execute retry :
                    - retry count: {}
                    - error code : {}
                    - isRetryable : {}
                """,
            retrySignal.totalRetries(),
            failure.getErrorCode(),
            failure.isRetryableError()
        );
    }

    private PaymentExecutionResult mapTo(TossPaymentConfirmResponse response) {
        return PaymentExecutionResult.of(
            response.paymentKey(),
            response.orderId(),
            ApprovalStatus.valueOf(response.status()),
            new ApproveDetails(
                response.orderName(),
                BigDecimal.valueOf(response.totalAmount()),
                PaymentMethod.valueOfDescription(response.method()),
                LocalDateTime.parse(response.approvedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            ),
            response.failure()
        );
    }

    private static boolean isRetryableThrows(Throwable throwable) {
        if (throwable instanceof PaymentApproveException paymentException) {
            return paymentException.isRetryableError();
        } else {
            return throwable instanceof TimeoutException;
        }
    }

    private static BiFunction<RetryBackoffSpec, RetrySignal, Throwable> getRetryExhausted() {
        return (retryBackoffSpec, retrySignal) -> {
            Throwable failure = retrySignal.failure();
            if (failure instanceof PaymentApproveException) {
                return failure;
            } else {
                return new PaymentApproveException(PAYMENT_APPROVED_TIMEOUT, failure);
            }
        };
    }

}
