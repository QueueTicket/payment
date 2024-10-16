package com.qticket.payment.adapter.in.web.api;

import com.qticket.payment.adapter.in.web.api.request.TossPaymentConfirmRequest;
import com.qticket.payment.application.port.in.PaymentConfirmUseCase;
import com.qticket.payment.domain.confirm.PaymentConfirmResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentConfirmUseCase paymentConfirmUseCase;

    @PostMapping("/confirm/widget")
    Mono<ResponseEntity<PaymentConfirmResult>> confirm(@RequestBody TossPaymentConfirmRequest request) {
        return paymentConfirmUseCase.confirm(request.toCommand())
            .map(it -> ResponseEntity.ok().body(it));
    }

}
