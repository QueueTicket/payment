package com.qticket.payment.adapter.out.web.external.payment.toss;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qticket.payment.adapter.out.web.external.payment.toss.response.error.TossPaymentsErrorCode;
import com.qticket.payment.application.port.in.command.PaymentApproveCommand;
import com.qticket.payment.config.base.SpringBootTestBase;
import com.qticket.payment.exception.adapter.external.PaymentApproveException;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.reactive.function.client.WebClient;

@DisplayName("Adapter:Out:Web:External:Payment:Toss:ErrorScenario")
class TossPaymentExecutorFailureTest extends SpringBootTestBase {

    private final WebClient tossPaymentsWebClient;
    private final ObjectMapper objectMapper;

    public TossPaymentExecutorFailureTest(
        WebClient tossPaymentsWebClient,
        ObjectMapper objectMapper
    ) {
        this.tossPaymentsWebClient = tossPaymentsWebClient;
        this.objectMapper = objectMapper;
    }

    private PaymentApproveCommand given = new PaymentApproveCommand(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        100_000L
    );

    @ParameterizedTest(name = "[{index}] - Scenario: {0}")
    @MethodSource("provideErrorScenarios")
    @DisplayName("Toss 결제 실패 시나리오 API")
    void paymentConfirmErrorScenarios(TossPaymentsErrorCode given) {
        // Given
        TossPaymentExecutor tossPaymentExecutor = new TossPaymentExecutor(
            errorScenarioTestWebClient(given),
            objectMapper,
            "/v1/payments/key-in"
        );

        // When & Then
        assertThatExceptionOfType(PaymentApproveException.class)
            .isThrownBy(() -> tossPaymentExecutor.execute(this.given).block())
            .satisfies(e -> {
                assertThat(e.isRetryableError()).isEqualTo(given.isRetryableError());
            })
        ;
    }

    private static Stream<TossPaymentsErrorCode> provideErrorScenarios() {
        return Arrays.stream(TossPaymentsErrorCode.values());
    }

    private WebClient errorScenarioTestWebClient(TossPaymentsErrorCode errorScenarioCode) {
        return tossPaymentsWebClient.mutate()
            .defaultHeader("TossPayments-Test-Code", errorScenarioCode.name())
            .build();
    }

}
