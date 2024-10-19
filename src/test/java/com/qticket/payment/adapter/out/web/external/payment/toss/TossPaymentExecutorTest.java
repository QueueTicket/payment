package com.qticket.payment.adapter.out.web.external.payment.toss;

import com.qticket.payment.adapter.out.web.external.payment.executor.PaymentExecutor;
import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.config.wiremock.WiremockTestBase;
import com.qticket.payment.domain.confirm.PaymentExecutionResult;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

@DisplayName("Adapter:Out:Web:External:Payment:Toss")
@TestPropertySource(properties = "pg.toss.endpoint=http://localhost:${wiremock.server.port}")
class TossPaymentExecutorTest extends WiremockTestBase {

    private final PaymentExecutor paymentExecutor;

    public TossPaymentExecutorTest(PaymentExecutor paymentExecutor) {
        this.paymentExecutor = paymentExecutor;
    }

    @Test
    @DisplayName("Toss 결제 승인 API")
    void paymentConfirm() {
        // Given
        PaymentConfirmCommand given = new PaymentConfirmCommand(
            "payment-key",
            UUID.randomUUID().toString(),
            120_000L
        );

        // When
        Mono<PaymentExecutionResult> execute = paymentExecutor.execute(given);

        // Then
        System.out.println(execute.block());
    }

}
