package com.qticket.payment.adapter.in.web.api;

import com.qticket.payment.adapter.in.web.api.request.TossPaymentConfirmRequest;
import com.qticket.payment.adapter.out.web.external.payment.toss.TossPaymentExecutor;
import com.qticket.payment.application.port.in.PaymentConfirmUseCase;
import com.qticket.payment.application.port.in.command.PaymentConfirmCommand;
import com.qticket.payment.domain.confirm.PaymentConfirmResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    private final TossPaymentExecutor tossPaymentExecutor;
    private final PaymentConfirmUseCase paymentConfirmUseCase;

    @PostMapping("/confirm/widget")
    Mono<ResponseEntity<PaymentConfirmResult>> confirm(@RequestBody TossPaymentConfirmRequest request) {
        log.info("{}", request);
        PaymentConfirmCommand command = request.toCommand();

        return paymentConfirmUseCase.confirm(command)
            .map(it -> ResponseEntity.ok()
                .body(it)
            );
    }

}
