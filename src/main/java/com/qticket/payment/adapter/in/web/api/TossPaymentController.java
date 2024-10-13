package com.qticket.payment.adapter.in.web.api;

import com.qticket.payment.adapter.in.web.api.request.TossPaymentConfirmRequest;
import com.qticket.payment.adapter.out.web.executor.TossPaymentExecutor;
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
public class TossPaymentController {

    private final TossPaymentExecutor tossPaymentExecutor;

    @PostMapping("/confirm/widget")
    Mono<ResponseEntity<String>> confirm(@RequestBody TossPaymentConfirmRequest request) {
        log.info("{}", request);
        return tossPaymentExecutor.execute(request).map(
            it -> ResponseEntity.ok().body(it)
        );
    }

}
